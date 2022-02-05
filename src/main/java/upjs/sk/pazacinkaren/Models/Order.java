package upjs.sk.pazacinkaren.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*
  Entita, ktorá v sebe uchováva inštatné premenne, getre, setre a toString metodu. 
 */

public class Order {

	private Long id;
	private LocalDateTime date;
	private String address;
	private Long idPalacinka;
	private Long idUser;
	private Map<Palacinka, Integer> kusyPalaciniek = new HashMap<>();

	public Order(Long id, LocalDateTime date, String adress, Long idPalacinka, Long idUser,
			Map<Palacinka, Integer> kusyPalaciniek) {
		this.id = id;
		this.date = date;
		this.address = adress;
		this.idPalacinka = idPalacinka;
		this.idUser = idUser;
		this.kusyPalaciniek = kusyPalaciniek;
	}

	public Order(LocalDateTime date, String address, Long idUser, Map<Palacinka, Integer> mapa) {
		this.date = date;
		this.address = address;
		this.idUser = idUser;
		this.kusyPalaciniek = mapa;
	}

	public Order(LocalDateTime date, String address, Long idUser) {
		this.date = date;
		this.address = address;
		this.idUser = idUser;
	}

	public Order(Long id, LocalDateTime date, String address, Long user_id) {
		this.id = id;
		this.date = date;
		this.address = address;
		this.idUser = user_id;

	}

	public Order() {

	}

	public Map<Palacinka, Integer> getKusyPalaciniek() {
		return kusyPalaciniek;
	}

	public void setKusyPalaciniek(Map<Palacinka, Integer> kusyPalaciniek) {
		this.kusyPalaciniek = kusyPalaciniek;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getIdPalacinka() {
		return idPalacinka;
	}

	public void setIdPalacinka(Long idPalacinka) {
		this.idPalacinka = idPalacinka;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + dateFormatter(date) + ", address=" + address + ", idUser=" + idUser + "]";
	}

	public String dateFormatter(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss");
		return formatter.format(date);
	}

	public String timeFormatter(LocalDateTime date) {
		String datum = dateFormatter(date);
		String[] cas = datum.split(" ");
		return cas[1];
	}

}
