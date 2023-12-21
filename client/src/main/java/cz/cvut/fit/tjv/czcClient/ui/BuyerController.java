package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Buyer;
import cz.cvut.fit.tjv.czcClient.domain.BuyerId;
import cz.cvut.fit.tjv.czcClient.domain.Product;
import cz.cvut.fit.tjv.czcClient.service.BuyerService;
import cz.cvut.fit.tjv.czcClient.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyers")
public class BuyerController {
    private final BuyerService buyerService;

    private final ProductService productService;

    public BuyerController(BuyerService buyerService, ProductService productService) {
        this.buyerService = buyerService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public String addToDb(Model model, @ModelAttribute Buyer buyer){
        buyerService.create(buyer);
        return "redirect:/buyers";
    }

    @PostMapping("/edit")
    public String editInDb(Model model, @ModelAttribute Buyer buyer){
        buyerService.setCurrentBuyer(buyer.getId());
        buyerService.update(buyer);
        return "redirect:/buyers/"+buyer.getId();
    }

    @PostMapping("/addBought/{productId}")
    public String addBought(Model model, @PathVariable Long productId, @ModelAttribute BuyerId buyerId){
        productService.setCurrentProduct(productId);
        Product pr = productService.getOne().get();
        if(pr.getNumberOfAvailable()<1){
            model.addAttribute("nothingOnStock",true);
            model.addAttribute("currentProduct",pr);
            return "productDetails";
        }
        try{
            buyerService.buy(buyerId.getId(),productId);
            pr.setNumberOfAvailable(pr.getNumberOfAvailable()-1);
            productService.update(pr);
        } catch (Exception e){
            model.addAttribute("invalidBuyerId",true);
            model.addAttribute("currentProduct",pr);
            return "productDetails";
        }
        return "redirect:/products";
    }
    @GetMapping()
    public String buyerList(Model model){
        var all = buyerService.getAll();
        model.addAttribute("allBuyers",all);
        return "buyers";
    }

    @GetMapping("/{id}")
    public String buyerDetails(Model model, @PathVariable Long id){
        buyerService.setCurrentBuyer(id);
        Buyer buyer = buyerService.getOne().get();
        model.addAttribute("currentBuyer",buyer);
        return "buyerDetails";
    }

    @GetMapping("/add")
    public String addNew(Model model){
        model.addAttribute("newBuyer",new Buyer());
        return "addBuyer";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id){
        buyerService.setCurrentBuyer(id);
        var buyer = buyerService.getOne().get();
        model.addAttribute("toEditBuyer",buyer);
        return "editBuyer";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Long id){
        buyerService.setCurrentBuyer(id);
        buyerService.delete();
        return "redirect:/buyers";
    }
}
