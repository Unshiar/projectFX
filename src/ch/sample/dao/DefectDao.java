package ch.sample.dao;

import ch.sample.model.Defect;

import java.util.List;

public interface DefectDao {
    Defect findById(int id);
    void save(Defect defect);
    void delete(Defect defect);
    void update(Defect defect);
    List<Defect> findAll();
}
