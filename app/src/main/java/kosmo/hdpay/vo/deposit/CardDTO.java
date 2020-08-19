package kosmo.hdpay.vo.deposit;
//{"CARD_NUM":"1","AC_BALANCE":1001}
public class CardDTO {
    private int CARD_NUM, AC_BALANCE;

    public int getCard_num() {
        return CARD_NUM;
    }

    public void setCard_num(int CARD_NUM) {
        this.CARD_NUM = CARD_NUM;
    }

    public int getAc_balance() {
        return AC_BALANCE;
    }

    public void setAc_balance(int AC_BALANCE) {
        this.AC_BALANCE = AC_BALANCE;
    }
}
