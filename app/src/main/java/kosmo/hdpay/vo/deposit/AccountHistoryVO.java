package kosmo.hdpay.vo.deposit;

public class AccountHistoryVO {
	
	private int r_num,sp_code;
	private String dealdate,dep_money,wit_money,balance,name;
	
	
	public int getSp_code() {
		return sp_code;
	}
	public void setSp_code(int sp_code) {
		this.sp_code = sp_code;
	}
	public int getR_num() {
		return r_num;
	}
	public void setR_num(int r_num) {
		this.r_num = r_num;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getDep_money() {
		return dep_money;
	}
	public void setDep_money(String dep_money) {
		this.dep_money = dep_money;
	}
	public String getWit_money() {
		return wit_money;
	}
	public void setWit_money(String wit_money) {
		this.wit_money = wit_money;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
