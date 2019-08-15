package ch.sample.services;

import ch.sample.dao.CartridgeDaoImpl;
import ch.sample.model.Cartridge;

import java.util.List;

public class CartridgeService {
    private CartridgeDaoImpl cartridgeDaoImpl = new CartridgeDaoImpl();

    public CartridgeService() {
    }

    public Cartridge findCartridge(int id) {
        return cartridgeDaoImpl.findById(id);
    }

    public void saveCartridge(Cartridge cartridge) {
        cartridgeDaoImpl.save(cartridge);
    }

    public void deleteCartridge(Cartridge cartridge) {
        cartridgeDaoImpl.delete(cartridge);
    }

    public void updateCartridge(Cartridge cartridge) {
        cartridgeDaoImpl.update(cartridge);
    }

    public List<Cartridge> findAllCartridges() {
        return cartridgeDaoImpl.findAll();
    }
}
