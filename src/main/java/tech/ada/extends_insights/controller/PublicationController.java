package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.enums.Tag;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.repository.PublicationRepository;

import java.util.List;
import java.util.Optional;

@RestController("/publications")
public class PublicationController {
    private final PublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationController(PublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/publications-items")
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationRequest request) {

        Publication convertedPublication = modelMapper.map(request, Publication.class);

        Publication newPublication = publicationRepository.save(convertedPublication);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPublication);
    }

    @GetMapping("/publications-items")
    public ResponseEntity<List<Publication>> getAllPublications() {
        List<Publication> allPublications = publicationRepository.findAll();
        return ResponseEntity.ok(allPublications);
    }

    @GetMapping(value = "/publications-items", params = {"title"})
    public ResponseEntity<List<Publication>> getPublicationByTitle(@RequestParam String title) {
        List<Publication> publicationByTitle = publicationRepository.findByTitle(title);
        if(publicationByTitle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTitle);
    }

    @GetMapping(value = "/publications-items", params = {"category"})
    public ResponseEntity<List<Publication>> getPublicationByCategory(@RequestParam Category category) {
        List<Publication> publicationByCategory = publicationRepository.findByCategory(category);
        if(publicationByCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByCategory);
    }

    @GetMapping(value = "/publications-items", params = {"tag"})
    public ResponseEntity<List<Publication>> getPublicationByTag(@RequestParam Tag tag) {
        List<Publication> publicationByTag = publicationRepository.findByTag(tag);
        if(publicationByTag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTag);
    }

    @GetMapping(value = "/publications-items", params = {"user"})
    public ResponseEntity<List<Publication>> getPublicationByUser(@RequestParam User user) {
        List<Publication> publicationByUser = publicationRepository.findByUser(user);
        if(publicationByUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByUser);
    }

    @DeleteMapping("/publications-items/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        publicationRepository.delete(publicationOptional.get());
        return ResponseEntity.noContent().build();
    }

}