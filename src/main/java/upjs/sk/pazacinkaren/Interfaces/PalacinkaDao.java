package upjs.sk.pazacinkaren.Interfaces;

import java.util.List;

import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Models.Palacinka;

public interface PalacinkaDao {

	List<Palacinka> getAll();

	Palacinka savePalacinka(Palacinka palacinka) throws EntityNotFoundException;
	
	Palacinka getByName(String palacinkaName) throws EntityNotFoundException;

	Palacinka deletePalacinka(Palacinka palacinka) throws EntityNotFoundException;
	
	Palacinka getPalacinkaById(long palacinkaId) throws EntityNotFoundException;
}
