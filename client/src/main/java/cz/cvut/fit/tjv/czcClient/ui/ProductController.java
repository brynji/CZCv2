package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping()
    public String listAll(Model model, @ModelAttribute Filters filters){
        var all = productService.getAll(filters);
        model.addAttribute("allProducts",all);
        //model.addAttribute("filters",filters);
        return "products";
    }
}