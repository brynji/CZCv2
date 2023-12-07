package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.domain.Product;
import cz.cvut.fit.tjv.czcClient.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @PostMapping("/add")
    public String addToDb(Model model, @ModelAttribute Product product){
        productService.create(product);
        return "redirect:/products";
    }

    @PostMapping("/edit")
    public String editInDb(Model model, @ModelAttribute Product product){
        productService.setCurrentProduct(product.getId());
        productService.update(product);
        return "redirect:/products/"+product.getId();
    }

    @GetMapping()
    public String listAll(Model model, @ModelAttribute Filters filters){
        var all = productService.getAll(filters);
        model.addAttribute("allProducts",all);
        model.addAttribute("filters",filters);
        return "products";
    }
    @GetMapping("/{id}")
    public String details(Model model, @PathVariable Long id){
        productService.setCurrentProduct(id);
        Optional<Product> product = productService.getOne();
        model.addAttribute("currentProduct",product.get());
        return "productDetails";
    }

    @GetMapping("/add")
    public String addNew(Model model){
        model.addAttribute("newProduct",new Product());
        return "addProduct";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id){
        productService.setCurrentProduct(id);
        var product = productService.getOne();
        model.addAttribute("toEditProduct",product.get());
        return "editProduct";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Long id){
        productService.setCurrentProduct(id);
        productService.delete();
        return "redirect:/products";
    }

}