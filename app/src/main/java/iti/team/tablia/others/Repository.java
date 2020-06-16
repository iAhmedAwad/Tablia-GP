package iti.team.tablia.others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import iti.team.tablia.ChefHome.ChefItemDetails;
import iti.team.tablia.ChefHome.TabBar.Menu.PojoMenu.MenuPojo;
import iti.team.tablia.ChefHome.TabBar.Order.OrderPojo;
import iti.team.tablia.CustomerAccount.CustomerOrder.CompleteOrder;
import iti.team.tablia.Models.CartGroupPojo;
import iti.team.tablia.Models.CartPojo;
import iti.team.tablia.Models.Chat;
import iti.team.tablia.Models.ChatList;
import iti.team.tablia.Models.ChatUser;
import iti.team.tablia.Models.Chef.ChefAccountSettings;
import iti.team.tablia.Models.Chef.ChefSettings;
import iti.team.tablia.Models.CustOrderPojo;
import iti.team.tablia.Models.Customer.CustomerAccountSettings;
import iti.team.tablia.Models.Customer.CustomerSettings;
import iti.team.tablia.Models.Others.Review;
import iti.team.tablia.Models.Rating;
import iti.team.tablia.Models.User;
import iti.team.tablia.R;
import iti.team.tablia.Services.APIService;
import iti.team.tablia.others.Client;
import iti.team.tablia.others.Data;
import iti.team.tablia.others.MyResponse;
import iti.team.tablia.others.Sender;
import iti.team.tablia.others.Token;
import iti.team.tablia.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private FirebaseUser firebaseUser;
    private Set<String> usersList;
    //    private List<ChatUser> chatUserList;
    private List<OrderPojo> orderPojos;
    private List<Chat> chats;
    private String lastMsg;
    private DatabaseReference reference;
    private APIService apiService;
    private boolean notify = false;
    private ValueEventListener valueEventListener;
    private ValueEventListener seenEventListener;
    private List<ChatUser> chefList;
    private MutableLiveData<List<ChatUser>> chefListLiveData = new MutableLiveData<>();

    public Repository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

    }

    /**
     * retrieve a list of users in chat list
     *
     * @return
     */
    public MutableLiveData<List<ChatUser>> getCustChatList() {
        final List<ChatList> list = new ArrayList<>();
        final MutableLiveData<List<ChatUser>> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("cust_chat_list")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    list.add(chatList);
                }
                readChat(list, liveData, "cust");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<List<ChatUser>> getChefChatList() {
        final List<ChatList> list = new ArrayList<>();
        final MutableLiveData<List<ChatUser>> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("chef_chat_list")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    list.add(chatList);
                }
                readChat(list, liveData, "chef");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    /**
     * get users objects from firebase users to list chat users
     *
     * @param userChatList
     */
    private void readChat(final List<ChatList> userChatList, final MutableLiveData<List<ChatUser>> liveData, String type) {

        final List<ChatUser> chatUserList = new ArrayList<>();
        if (type.equals("chef")) {
            reference = FirebaseDatabase.getInstance().getReference("customer_account_settings");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatUserList.clear();
                    for (ChatList chatList : userChatList) {
                        CustomerAccountSettings settings = dataSnapshot.child(chatList.getCustId())
                                .getValue(CustomerAccountSettings.class);
                        ChatUser user = new ChatUser(chatList.getCustId(), settings.getDisplayName()
                                , settings.getProfilePhoto(), settings.getStatus(), chatList.getLastMsgTime(), chatList.getLastMsg());
                        chatUserList.add(user);
                    }
                    liveData.setValue(chatUserList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            reference = FirebaseDatabase.getInstance().getReference("chef_account_settings");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatUserList.clear();
                    for (ChatList chatList : userChatList) {
                        ChefAccountSettings settings = dataSnapshot.child(chatList.getChefId())
                                .getValue(ChefAccountSettings.class);
                        if(settings.isAvailable()) {
                            ChatUser user = new ChatUser(chatList.getChefId(), settings.getDisplayName()
                                    , settings.getProfilePhoto(), settings.getStatus(), chatList.getLastMsgTime(), chatList.getLastMsg());
                            chatUserList.add(user);
                        }
                    }
                    liveData.setValue(chatUserList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
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

    public MutableLiveData<ChefAccountSettings> getCurrentUser() {
        final MutableLiveData<ChefAccountSettings> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings").child(firebaseUser.getUid());
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

    /**
     * update user current status if they are offline or online for chat
     *
     * @param status
     */
    public void custStatus(String status) {
        FirebaseDatabase.getInstance().getReference("customer_account_settings").child(firebaseUser.getUid()).child("status").setValue(status);
//        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings").child(firebaseUser.getUid());
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", status);
//        reference.updateChildren(hashMap);
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
        setChatList(sender, receiver, message);
    }

    private void setChatList(final String sender, final String receiver, final String message) {
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(sender).getValue(User.class);
                ChatList chatList = new ChatList();
                chatList.setLastMsg(message);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                chatList.setLastMsgTime(dateFormat.format(date));

                if (user.getType().equals("chef")) {
                    chatList.setChefId(sender);
                    chatList.setCustId(receiver);
                } else {
                    chatList.setChefId(receiver);
                    chatList.setCustId(sender);
                }

                FirebaseDatabase.getInstance().getReference("chef_chat_list")
                        .child(chatList.getChefId())
                        .child(chatList.getCustId())
                        .setValue(chatList);
                FirebaseDatabase.getInstance().getReference("cust_chat_list")
                        .child(chatList.getCustId())
                        .child(chatList.getChefId())
                        .setValue(chatList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                if (Repository.this.notify) {
                    sendNotification(receiver, user.getUsername(), message, context);
                }
                Repository.this.notify = false;
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
                        chefAccountSettings.getStatus(), "none", "none");
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
                        settings.getStatus(), "none", "none");
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
        Query query = FirebaseDatabase.getInstance().getReference("chef_account_settings").orderByChild("rating").limitToLast(20);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChefAccountSettings user = snapshot.getValue(ChefAccountSettings.class);
                    if(user.isAvailable()) {
                        String chefId = snapshot.getKey();
                        ChatUser chatUser = new ChatUser(chefId, user.getUserName(), user.getProfilePhoto()
                                , user.getStatus(), "none", "none");
                        chefList.add(chatUser);
                    }

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
                        if(user.isAvailable()) {
                            String chefId = snapshot.getKey();
                            ChatUser chatUser = new ChatUser(chefId, user.getUserName()
                                    , user.getProfilePhoto(), user.getStatus(), "none", "none");
                            chefList.add(chatUser);
                        }

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

    public MutableLiveData<Double> getOrdersAmount() {
        final MutableLiveData<Double> salesAmountLiveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("orders")
                .child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double salesAmount = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1 : data.getChildren()) {

                        OrderPojo pojo = dataSnapshot1.getValue(OrderPojo.class);
                        if (pojo.isChefConfirm() && pojo.isCustConfirm()) {
                            salesAmount += pojo.getSubTotal();
                        }
                    }

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
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public MutableLiveData<MenuPojo> getDisItemDetails(String chefId, String itemId) {
        final MutableLiveData<MenuPojo> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("Disable").child(chefId).child(itemId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public MutableLiveData<CustomerAccountSettings> getCustInfo(String custId) {
        final MutableLiveData<CustomerAccountSettings> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("customer_account_settings")
                .child(custId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public MutableLiveData<Boolean> checkOrderItemsInMenu(final OrderPojo orderPojo, final CompleteOrder completeOrder) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        final List<Boolean> list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(orderPojo.getChefID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (CartPojo pojo : orderPojo.getItems()) {
                    if (dataSnapshot.hasChild(pojo.getItemID())) {
                        MenuPojo menuPojo = dataSnapshot.child(pojo.getItemID()).getValue(MenuPojo.class);
                        if (menuPojo.getItemQuantity() >= pojo.getQuantity()) {
                            list.add(true);
                        } else {
                            list.add(false);
                        }
                    }
                }
                if (!list.contains(false)) {
                    updateMenuQty(orderPojo, completeOrder);
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

    public void updateMenuQty(final OrderPojo orderPojo, final CompleteOrder completeOrder) {
        reference = FirebaseDatabase.getInstance().getReference("menu")
                .child(orderPojo.getChefID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (CartPojo pojo : orderPojo.getItems()) {
                    if (dataSnapshot.hasChild(pojo.getItemID())) {
                        MenuPojo menuPojo = dataSnapshot.child(pojo.getItemID()).getValue(MenuPojo.class);
                        if (menuPojo.getItemQuantity() >= pojo.getQuantity()) {
                            int newQty = menuPojo.getItemQuantity() - pojo.getQuantity();
                            menuPojo.setItemQuantity(newQty);
                            FirebaseDatabase.getInstance().getReference("menu")
                                    .child(orderPojo.getChefID())
                                    .child(menuPojo.getItemID()).setValue(menuPojo);
                        }
                    }
                }
                saveOrder(orderPojo, completeOrder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveOrder(OrderPojo orderPojo, CompleteOrder completeOrder) {
        reference = FirebaseDatabase.getInstance().getReference("orders")
                .child(orderPojo.getChefID())
                .child(firebaseUser.getUid());
        String key = reference.push().getKey();
        orderPojo.setOrderID(key);
        orderPojo.setCustomerID(firebaseUser.getUid());
        final OrderPojo pojo = orderPojo;
        final CompleteOrder completeOrder1 = completeOrder;
        reference.child(key).setValue(orderPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    chefOrdersNum(pojo.getChefID());
                    saveCustOrder(pojo, completeOrder1);
                }
            }
        });

    }

    public void removeCartOrder(OrderPojo orderPojo, final CompleteOrder completeOrder) {
        FirebaseDatabase.getInstance().getReference("cart")
                .child(firebaseUser.getUid())
                .child(orderPojo.getChefID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                completeOrder.progressBar.setVisibility(View.GONE);
                completeOrder.finish();
            }
        });
    }

    public void deleteMenuItem(String chefId, String itemId) {
        FirebaseDatabase.getInstance().getReference("menu")
                .child(chefId)
                .child(itemId).removeValue();
    }

    public void deleteDisabledItem(String chefId, String itemId) {
        FirebaseDatabase.getInstance().getReference("Disable")
                .child(chefId)
                .child(itemId).removeValue();
    }

    public void addDisabledToMenu(MenuPojo menuPojo) {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("menu");

        //add data to the "customers" node
        reference.child(userid)
                .child(menuPojo.getItemID())
                .setValue(menuPojo);
        FirebaseDatabase.getInstance().getReference("Disable")
                .child(userid)
                .child(menuPojo.getItemID()).removeValue();

    }

    public void disableMenuItem(String chefId, String itemId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("disabled", true);
        FirebaseDatabase.getInstance().getReference("menu")
                .child(chefId)
                .child(itemId).updateChildren(hashMap);
    }

    public MutableLiveData<List<OrderPojo>> getCustOrders() {
        final MutableLiveData<List<OrderPojo>> liveData = new MutableLiveData<>();
        orderPojos = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("cust_orders").child(firebaseUser.getUid()).getChildren()) {
                    CustOrderPojo custOrderPojo = snapshot.getValue(CustOrderPojo.class);
                    if (!custOrderPojo.isConfirmed()) {
                        OrderPojo orderPojo = dataSnapshot.child("orders")
                                .child(custOrderPojo.getChefId())
                                .child(firebaseUser.getUid())
                                .child(custOrderPojo.getOrderId()).getValue(OrderPojo.class);
                        orderPojos.add(orderPojo);

                    }
                }
                liveData.setValue(orderPojos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<List<OrderPojo>> getCustOrdersHistory() {
        final MutableLiveData<List<OrderPojo>> liveData = new MutableLiveData<>();
        orderPojos = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("cust_orders").child(firebaseUser.getUid()).getChildren()) {
                    CustOrderPojo custOrderPojo = snapshot.getValue(CustOrderPojo.class);
                    if (custOrderPojo.isConfirmed()) {
                        OrderPojo orderPojo = dataSnapshot.child("orders")
                                .child(custOrderPojo.getChefId())
                                .child(firebaseUser.getUid())
                                .child(custOrderPojo.getOrderId()).getValue(OrderPojo.class);
                        orderPojos.add(orderPojo);

                    }
                }
                liveData.setValue(orderPojos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public void saveCustOrder(final OrderPojo orderPojo, final CompleteOrder completeOrder) {
        CustOrderPojo custOrderPojo = new CustOrderPojo(orderPojo.getChefID(), orderPojo.getOrderID());
        reference = FirebaseDatabase.getInstance().getReference("cust_orders")
                .child(firebaseUser.getUid());
        reference.child(orderPojo.getOrderID()).setValue(custOrderPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    custOrdersNum();
                    removeCartOrder(orderPojo, completeOrder);
                }
            }
        });
    }

    private void chefOrdersNum(final String chefId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chef_account_settings")
                .child(chefId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefAccountSettings settings = dataSnapshot.getValue(ChefAccountSettings.class);
                settings.setOrders(settings.getOrders() + 1);
                FirebaseDatabase.getInstance().getReference("chef_account_settings")
                        .child(chefId).setValue(settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void custOrdersNum() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customer_account_settings")
                .child(firebaseUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CustomerAccountSettings settings = dataSnapshot.getValue(CustomerAccountSettings.class);
                settings.setOrders(settings.getOrders() + 1);
                FirebaseDatabase.getInstance().getReference("customer_account_settings")
                        .child(firebaseUser.getUid()).setValue(settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public MutableLiveData<ChefAccountSettings> getChefData() {
        final MutableLiveData<ChefAccountSettings> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("chef_account_settings")
                .child(firebaseUser.getUid());
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

    public MutableLiveData<Integer> getTodysOrders() {
        final MutableLiveData<Integer> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("orders")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderPojo orderPojo = snapshot1.getValue(OrderPojo.class);
//                        try {
                           /* Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                    .parse(orderPojo.getOrderTime());
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            date = dateFormat.parse(dateFormat.format(date));

                            Date date1 = new Date();
                            DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                            date1 = dateFormat1.parse(dateFormat1.format(date1));*/


                        if (!orderPojo.isChefConfirm() && !orderPojo.isCustConfirm()) {
                            counter++;
                        }
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }

                    }
                }
                liveData.setValue(counter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public MutableLiveData<OrderPojo> getOrder(String orderID, String chefID, String custID) {
        final MutableLiveData<OrderPojo> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderPojo orderPojo = dataSnapshot.getValue(OrderPojo.class);
                liveData.setValue(orderPojo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public void updateOrder(String orderID, String chefID, String custID, double newShippingFee) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("deliveryFee", newShippingFee);
        FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID).updateChildren(hashMap);
    }

    public void updateOrderDelTime(String orderID, String chefID, String custID, String deliveryTime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("deliveryTime", deliveryTime);
        FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID).updateChildren(hashMap);
    }

    public void updateOrderChefConfirm(String orderID, String chefID, String custID) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("chefConfirm", true);
        FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID).updateChildren(hashMap);
    }

    public void updateOrderCustConfirm(String orderID, String chefID, String custID) {

        HashMap<String, Object> hashMap0 = new HashMap<>();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        hashMap0.put("deliveryTime", dateFormat.format(date));
        FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID).updateChildren(hashMap0);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("custConfirm", true);
        FirebaseDatabase.getInstance().getReference("orders")
                .child(chefID)
                .child(custID)
                .child(orderID).updateChildren(hashMap);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("confirmed", true);
        FirebaseDatabase.getInstance().getReference("cust_orders")
                .child(custID)
                .child(orderID).updateChildren(hashMap2);
    }

    public MutableLiveData<List<OrderPojo>> getChefOrdersHistory() {
        final MutableLiveData<List<OrderPojo>> order = new MutableLiveData<>();
        final List<OrderPojo> list = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders")
                .child(firebaseUser.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1 : data.getChildren()) {


                        OrderPojo pojo = dataSnapshot1.getValue(OrderPojo.class);
                        if (pojo.isChefConfirm() && pojo.isCustConfirm()) {
                            list.add(pojo);
                        }
                    }

                }

                order.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return order;
    }

    public MutableLiveData<List<Review>> getReviewsCountAndRating(String itemId) {
        final List<Review> reviews = new ArrayList<>();
        final MutableLiveData<List<Review>> liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("reviews")
                .child(itemId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    reviews.add(review);
                }
                liveData.setValue(reviews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return liveData;
    }

    public void saveCustRating(final String chefId, String custId, float rating) {
        Rating ratingPojo = new Rating(rating);
        reference = FirebaseDatabase.getInstance().getReference("chef_rating")
                .child(chefId)
                .child(custId);
        reference.setValue(ratingPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateChefRating(chefId);
                }
            }
        });
    }

    private void updateChefRating(final String chefId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chef_rating")
                .child(chefId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float totalRating = 0;
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    count++;
                    Rating rating = snapshot.getValue(Rating.class);
                    totalRating += rating.getRating();
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                double rate = (totalRating / (float) count);
                hashMap.put("rating", rate);
                FirebaseDatabase.getInstance().getReference("chef_account_settings")
                        .child(chefId).updateChildren(hashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setStatusFromMsg(final String status) {
        reference = FirebaseDatabase.getInstance().getReference("users")
                .child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user.getType().equals("chef")){
                    reference = FirebaseDatabase.getInstance()
                            .getReference(Constants.chefAccountSettingsNode).child(firebaseUser.getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("status", status);
                    reference.updateChildren(hashMap);
                }else{
                    reference = FirebaseDatabase.getInstance()
                            .getReference(Constants.customerAccountSettingsNode).child(firebaseUser.getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("status", status);
                    reference.updateChildren(hashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}