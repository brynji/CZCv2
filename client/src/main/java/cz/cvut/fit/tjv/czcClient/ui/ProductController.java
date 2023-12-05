package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.domain.Product;
import cz.cvut.fit.tjv.czcClient.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

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
}