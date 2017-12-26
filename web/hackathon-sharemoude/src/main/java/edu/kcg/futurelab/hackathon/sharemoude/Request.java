package edu.kcg.futurelab.hackathon.sharemoude;

public class Request {
	public Request() {
	}
	public Request(int id, String pray, int money) {
		this.id = id;
		this.pray = pray;
		this.money = money;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPray() {
		return pray;
	}
	public void setPray(String pray) {
		this.pray = pray;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

	private int id;
	private String pray;
	private int money;
}
