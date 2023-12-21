package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.repository.BuyerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class BuyerServiceImpl extends CrudServiceImpl<Buyer,Long> implements BuyerService{
    private final BuyerRepository buyerRepository;

    @Override
    public void removeFromBought(Long productId) {
        Iterable<Buyer> buyers = buyerRepository.findAll();
        for (Buyer buyer : buyers) {
            buyer.getBoughtByMe().removeIf(product -> product.getId().equals(productId));
            buyerRepository.save(buyer);
        }
    }

    public BuyerServiceImpl(BuyerRepository buyerRepository){
        this.buyerRepository=buyerRepository;
    }
    @Override
    protected CrudRepository<Buyer, Long> getRepository() {
        return buyerRepository;
    }
}
