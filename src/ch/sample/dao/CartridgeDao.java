package ch.sample.dao;

import ch.sample.model.Cartridge;

import java.util.List;

public interface CartridgeDao {
    Cartridge findById(int id);
    void save(Cartridge cartridge);
    void delete(Cartridge cartridge);
    void update(Cartridge cartridge);
    List<Cartridge> findAll();
}
