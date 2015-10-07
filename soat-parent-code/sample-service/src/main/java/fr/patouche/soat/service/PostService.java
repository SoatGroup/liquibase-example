package fr.patouche.soat.service;

import java.util.List;
import java.util.Optional;

import fr.patouche.soat.entity.Post;

/**
 * Post service interface.
 *
 * @author : patouche - 06/10/15.
 */
public interface PostService {

    List<Post> getAll();

    Post create(String author, String title, String post);

    Optional<Post> read(Long id);

}
