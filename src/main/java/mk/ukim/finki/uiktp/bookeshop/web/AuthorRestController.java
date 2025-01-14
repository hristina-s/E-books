package mk.ukim.finki.uiktp.bookeshop.web;

import mk.ukim.finki.uiktp.bookeshop.model.Author;
import mk.ukim.finki.uiktp.bookeshop.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAll() {
        return this.authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        return this.authorService.findById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Author> create(@RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String birthYear) {
        return this.authorService.create(name, surname, birthYear)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id,
                                         @RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String birthYear) {
        return this.authorService.update(id, name, surname, birthYear)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.authorService.delete(id);
        if (this.authorService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
