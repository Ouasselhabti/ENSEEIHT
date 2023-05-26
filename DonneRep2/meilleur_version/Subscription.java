public class Subscription {
    public Client_itf client;
    public Callback_itf callback;

    public Subscription(Client_itf client, Callback_itf callback) {
        this.client = client;
        this.callback = callback;
    }
}