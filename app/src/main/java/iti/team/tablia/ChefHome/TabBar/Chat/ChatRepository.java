package iti.team.tablia.ChefHome.TabBar.Chat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chat;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Chef.ChefSettings;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.Services.APIService;
import iti.team.tablia.others.Client;
import iti.team.tablia.others.Data;
import iti.team.tablia.others.MyResponse;
import iti.team.tablia.others.Sender;
import iti.team.tablia.others.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatRepository {

    private FirebaseUser firebaseUser;
    private Set<String> usersList;
    private List<ChatUser> chatUserList;
    private List<Chat> chats;
    private String lastMsg;
    private DatabaseReference reference;
    private APIService apiService;
    private boolean notify = false;
    private ValueEventListener valueEventListener;
    private ValueEventListener seenEventListener;
    private List<ChatUser> chefList;
    private MutableLiveData<List<ChatUser>> chefListLiveData = new MutableLiveData<>();

    public ChatRepository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

    }

    /**
     * retrieve a list of users in chat list
     *
     * @return
     */
    public MutableLiveData<List<ChatUser>> getChatList() {
        final MutableLiveData<List<ChatUser>> userChatList = new MutableLiveData<>();
        usersList = new HashSet<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(firebaseUser.getUid())) {
                        if (!usersList.contains(chat.getReceiver())) {
                            usersList.add(chat.getReceiver());
                        }
                    }
                    if (chat.getReceiver().equals(firebaseUser.getUid())) {
                        if (!usersList.contains(chat.getSender())) {
                            usersList.add(chat.getSender());
                        }
                    }
                }
                readChat(userChatList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userChatList;
    }

    /**
     * get users objects from firebase users to list chat users
     *
     * @param userChatList
     */
    private void readChat(final MutableLiveData<List<ChatUser>> userChatList) {

        chatUserList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatUserList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("users").getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (usersList.contains(user.getUser_id())) {
                        Log.i("TAG", String.valueOf(R.string.typeChef));
                        if (user.getType().equals("chef")) {
                            ChefAccountSettings chef = dataSnapshot.child("chef_account_settings")
                                    .child(user.getUser_id())
                                    .getValue(ChefAccountSettings.class);
                            ChefSettings chefSettings = new ChefSettings(user, chef);
                            ChatUser chatUser = new ChatUser(chefSettings.getUser().getUser_id(),
                                    chefSettings.getUser().getUsername(),
                                    chefSettings.getChefAccountSettings().getProfilePhoto(),
                                    chefSettings.getChefAccountSettings().getStatus());
                            if (!chatUserList.contains(chatUser)) {
                                chatUserList.add(chatUser);
                            }
                        } else {
                            CustomerAccountSettings customer = dataSnapshot.child("customer_account_settings")
                                    .child(user.getUser_id())
                                    .getValue(CustomerAccountSettings.class);
                            CustomerSettings customerSettings = new CustomerSettings(user, customer);

                            ChatUser chatUser = new ChatUser(customerSettings.getUser().getUser_id(),
                                    customerSettings.getUser().getUsername(),
                                    customerSettings.getCustomerAccountSettings().getProfilePhoto(),
                                    customerSettings.getCustomerAccountSettings().getStatus());
                            if (!chatUserList.contains(chatUser)) {
                                chatUserList.add(chatUser);
                            }
                        }
                    }
                }

                userChatList.setValue(chatUserList);
                reference.removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * add new token of each user to tokens list in firebase
     *
     * @param activity
     */
    public void updateToken(Activity activity) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(activity, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens");
                Token token1 = new Token(newToken);
                reference.child(firebaseUser.getUid()).setValue(token1);
                Log.e("newToken", newToken);

            }
        });
    }

    /**
     * render last message between two users
     *
     * @param userId
     * @param last_msg
     */
    public void LastMsg(final String userId, final TextView last_msg) {
        lastMsg = "default";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null) {
                        if (chat.getReceiver().equals(userId) && chat.getSender().equals(firebaseUser.getUid()) ||
                                chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)) {
                            lastMsg = chat.getMessage();
                        }
                    }
                }
                last_msg.setText(lastMsg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * update user current status if they are offline or online for chat
     *
     * @param status
     */
    public void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    /**
     * update user current status if they are offline or online for chat
     *
     * @param status
     */
    public void custStatus(String status) {
        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    /**
     * save new message to firebase
     *
     * @param sender
     * @param receiver
     * @param message
     */
    public void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Chat chat = new Chat(sender, receiver, message, false);
        reference.child("Chats").push().setValue(chat);
    }

    /**
     * send notification to users when receiving message
     *
     * @param receiver
     * @param message
     * @param context
     * @param notify
     */
    public void notifyUser(final String receiver, final String message, final Context context, final boolean notify) {
        this.notify = notify;
        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (ChatRepository.this.notify) {
                    sendNotification(receiver, user.getUsername(), message, context);
                }
                ChatRepository.this.notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * handle notification with retrofit and setting notification data
     *
     * @param receiver
     * @param username
     * @param msg
     * @param context
     */
    private void sendNotification(final String receiver, final String username, final String msg, final Context context) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(), R.mipmap.ic_launcher, username + ":" + msg, "New Message", receiver);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success != 1) {
                                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * get receiver user info for massaging
     *
     * @param userId
     * @return
     */
    public MutableLiveData<ChatUser> openChatMessage(final String userId) {
        final MutableLiveData<ChatUser> userMutableLiveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getType().equals("chef")) {
                    getChef(userMutableLiveData, userId);

                } else {
                    getCustomer(userMutableLiveData, userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userMutableLiveData;
    }

    private void getChef(final MutableLiveData<ChatUser> user, final String userId) {
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings chefAccountSettings = dataSnapshot.getValue(ChefAccountSettings.class);
                ChatUser chatUser = new ChatUser(userId, chefAccountSettings.getUserName(),
                        chefAccountSettings.getProfilePhoto(),
                        chefAccountSettings.getStatus());
                user.setValue(chatUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getCustomer(final MutableLiveData<ChatUser> user, final String userId) {
        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                ChatUser chatUser = new ChatUser(userId, settings.getUserName(),
                        settings.getProfilePhoto(),
                        settings.getStatus());
                user.setValue(chatUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * retrieve chat data for each new and old messages
     *
     * @param myId
     * @param userId
     * @return
     */
    public MutableLiveData<List<Chat>> readMessage(final String myId, final String userId) {
        final MutableLiveData<List<Chat>> chatList = new MutableLiveData<>();
        chats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        chats.add(chat);
                    }
                    chatList.setValue(chats);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return chatList;
    }

    /**
     * update message status if its delivered or seen
     *
     * @param userId
     * @param isPaused
     */
    public void seenMessage(final String userId, boolean isPaused) {

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        if (!isPaused) {
            seenEventListener = reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = snapshot.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)) {
                            chat.setSeen(true);
                            snapshot.getRef().setValue(chat);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            reference.removeEventListener(seenEventListener);
        }
    }

    /**
     * render chef list
     *
     * @return
     */
    //done
    public MutableLiveData<List<ChatUser>> getChefList() {
        chefList = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("chef_account_settings").orderByChild("rating").limitToFirst(20);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChefAccountSettings user = snapshot.getValue(ChefAccountSettings.class);
                    String chefId = snapshot.getKey();
                    ChatUser chatUser = new ChatUser(chefId, user.getUserName(), user.getProfilePhoto(), user.getStatus());
                    chefList.add(chatUser);

                }
                chefListLiveData.setValue(chefList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return chefListLiveData;
    }

    /**
     * search in chef list
     *
     * @param s
     * @return
     */
    //done without empty text
    public MutableLiveData<List<ChatUser>> getChefSearchList(final String s) {
        chefList = new ArrayList<>();
        final MutableLiveData<List<ChatUser>> chefSearchLiveData = new MutableLiveData<>();
        Query query = FirebaseDatabase.getInstance().getReference("chef_account_settings").orderByChild("userName")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!s.equals("")) {
                    chefList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChefAccountSettings user = snapshot.getValue(ChefAccountSettings.class);
                        String chefId = snapshot.getKey();
                        ChatUser chatUser = new ChatUser(chefId, user.getUserName(), user.getProfilePhoto(), user.getStatus());
                        chefList.add(chatUser);

                    }
                    chefSearchLiveData.setValue(chefList);

                } else {
                    getChefList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return chefSearchLiveData;
    }

    public MutableLiveData<Float> getChefRate() {
        final MutableLiveData<Float> rateLiveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings")
                .child(firebaseUser.getUid()).child("rating");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float rate = dataSnapshot.getValue(Float.class);
                rateLiveData.setValue(rate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rateLiveData;
    }

    public MutableLiveData<Double> getOrdersAmount() {
        final MutableLiveData<Double> salesAmountLiveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("orders").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double salesAmount = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderPojo orderDeatils = snapshot.getValue(OrderPojo.class);
                    salesAmount += orderDeatils.getSubTotal();
                }
                salesAmountLiveData.setValue(salesAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return salesAmountLiveData;
    }

    public MutableLiveData<MenuPojo> getMenuItemDetails(String chefId, String itemId) {
        final MutableLiveData<MenuPojo> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("menu").child(chefId).child(itemId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuPojo menuPojo = dataSnapshot.getValue(MenuPojo.class);
                liveData.setValue(menuPojo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<ChefAccountSettings> getChefInfo(String chefId) {
        final MutableLiveData<ChefAccountSettings> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(chefId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings settings = dataSnapshot.getValue(ChefAccountSettings.class);
                liveData.setValue(settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<Boolean> checkItemExistInCart(String chefId, final String itemId) {
        final MutableLiveData<Boolean> isExistInCart = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("cart")
                .child(firebaseUser.getUid())
                .child(chefId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(itemId)) {
                    isExistInCart.setValue(true);
                } else {
                    isExistInCart.setValue(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return isExistInCart;
    }

    public MutableLiveData<Boolean> saveCartItem(final CartPojo cartPojo) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(cartPojo.getChefID())
                .child(cartPojo.getItemID());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuPojo menuPojo = dataSnapshot.getValue(MenuPojo.class);
                if (menuPojo.getItemQuantity() >= cartPojo.getQuantity()) {
                    FirebaseDatabase.getInstance().getReference("cart")
                            .child(firebaseUser.getUid())
                            .child(cartPojo.getChefID())
                            .child(cartPojo.getItemID()).setValue(cartPojo);
                    liveData.setValue(true);
                } else {
                    liveData.setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<Boolean> updateCartItem(final CartPojo cartPojo) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(cartPojo.getChefID())
                .child(cartPojo.getItemID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MenuPojo menuPojo = dataSnapshot.getValue(MenuPojo.class);
                if (menuPojo.getItemQuantity() >= cartPojo.getQuantity()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("quantity", cartPojo.getQuantity());

                    FirebaseDatabase.getInstance().getReference("cart")
                            .child(firebaseUser.getUid())
                            .child(cartPojo.getChefID())
                            .child(cartPojo.getItemID()).updateChildren(hashMap);
                    liveData.setValue(true);
                } else {
                    liveData.setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public void removeCartItem(final CartPojo cartPojo) {

        FirebaseDatabase.getInstance().getReference("cart")
                .child(firebaseUser.getUid())
                .child(cartPojo.getChefID())
                .child(cartPojo.getItemID()).removeValue();

    }

    public MutableLiveData<List<CartGroupPojo>> getCartListGroups() {
        final MutableLiveData<List<CartGroupPojo>> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("cart")
                .child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CartGroupPojo> pojos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<CartPojo> cartPojos = new ArrayList<>();
                    for (DataSnapshot cartItem : snapshot.getChildren()) {
                        CartPojo cartPojo = cartItem.getValue(CartPojo.class);
                        cartPojos.add(cartPojo);
                    }
                    CartGroupPojo pojo = new CartGroupPojo(snapshot.getKey(), cartPojos);
                    pojos.add(pojo);
                }
                liveData.setValue(pojos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return liveData;
    }

    public MutableLiveData<CustomerAccountSettings> getCustInfo() {
        final MutableLiveData<CustomerAccountSettings> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                liveData.setValue(settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<Boolean> checkOrderItemsInMenu(final OrderPojo orderPojo) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(orderPojo.getChefID());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (CartPojo pojo : orderPojo.getItems()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MenuPojo menuPojo = snapshot.getValue(MenuPojo.class);
                        if (menuPojo.getItemID().equals(pojo.getItemID())) {
                            if (menuPojo.getItemQuantity() >= pojo.getQuantity()) {
                                liveData.setValue(true);
                            } else {
                                liveData.setValue(false);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public void updateMenuQty(final OrderPojo orderPojo) {
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(orderPojo.getChefID());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (CartPojo pojo : orderPojo.getItems()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MenuPojo menuPojo = snapshot.getValue(MenuPojo.class);
                        if (menuPojo.getItemID().equals(pojo.getItemID())) {
                            if (menuPojo.getItemQuantity() >= pojo.getQuantity()) {
                                int newQty = menuPojo.getItemQuantity() - pojo.getQuantity();
                                menuPojo.setItemQuantity(newQty);
                                FirebaseDatabase.getInstance().getReference("menu")
                                        .child(orderPojo.getChefID())
                                        .child(menuPojo.getItemID()).setValue(menuPojo);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveOrder(OrderPojo orderPojo) {
        reference = FirebaseDatabase.getInstance().getReference("orders")
                .child(orderPojo.getChefID())
                .child(firebaseUser.getUid());
        String key = reference.push().getKey();
        orderPojo.setOrderID(key);
        orderPojo.setCustomerID(firebaseUser.getUid());
        reference.child(key).setValue(orderPojo);

    }

    public void removeCartOrder(OrderPojo orderPojo) {
        FirebaseDatabase.getInstance().getReference("cart")
                .child(firebaseUser.getUid())
                .child(orderPojo.getChefID()).removeValue();
    }
}
