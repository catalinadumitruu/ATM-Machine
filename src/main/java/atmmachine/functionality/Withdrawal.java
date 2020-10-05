package atmmachine.functionality;

public class Withdrawal extends Transaction {

    @Override
    public void executeTransaction(double amount) {
        // todo: check if user is connected
        // todo: withdrawal sum as param -> get available sum from db and compare it
        // todo: if available > amount => withdrawal & update in table, else give message

    }
}
