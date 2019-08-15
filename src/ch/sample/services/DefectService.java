package ch.sample.services;

import ch.sample.dao.DefectDaoImpl;
import ch.sample.model.Defect;

import java.util.List;

public class DefectService {
    private DefectDaoImpl defectDaoImpl = new DefectDaoImpl();

    public DefectService() {
    }

    public Defect findDefect(int id) {
        return defectDaoImpl.findById(id);
    }

    public void saveDefect(Defect defect) {
        defectDaoImpl.save(defect);
    }

    public void deleteDefect(Defect defect) {
        defectDaoImpl.delete(defect);
    }

    public void updateDefect(Defect defect) {
        defectDaoImpl.update(defect);
    }

    public List<Defect> findAllDefects() {
        return defectDaoImpl.findAll();
    }
}
