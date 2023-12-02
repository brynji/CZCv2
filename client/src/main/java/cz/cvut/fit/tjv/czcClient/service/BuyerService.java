package cz.cvut.fit.tjv.czcClient.service;

import cz.cvut.fit.tjv.czcClient.api_client.BuyerClient;
import cz.cvut.fit.tjv.czcClient.domain.Buyer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BuyerService {
    private BuyerClient buyerClient;
    private Long currentBuyer;

    public BuyerService(BuyerClient buyerClient) { this.buyerClient = buyerClient; }
    public boolean isCurrentBuyer(){ return currentBuyer!=null; }
    public void setCurrentBuyer(Long id){
        currentBuyer=id;
        buyerClient.setCurrentBuyer(id);
    }

    public void create(Buyer data){ buyerClient.create(data); }
    public Optional<Buyer> getOne(){ return buyerClient.getOne(); }
    public Collection<Buyer> getAll(){ return buyerClient.getAll(); }
    public void update(Buyer data){ buyerClient.update(data);}
}