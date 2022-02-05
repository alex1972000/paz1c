package upjs.sk.pazacinkaren.Models;

public class Role {

	private String nazov;
	private Long id;

	public Role(long id, String name) {
		this.id = id;
		this.nazov = name;
	}

	public Role() {

	}

	public Role(String name) {
		this.nazov = name;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String name) {
		this.nazov = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
