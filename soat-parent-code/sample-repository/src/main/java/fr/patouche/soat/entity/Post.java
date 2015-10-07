package fr.patouche.soat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

import org.hibernate.annotations.NaturalId;

/**
 * Post entity.
 *
 * @author patouche - 06/10/15
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Technical identifier. */
    @Id
    @Column(name = "id")
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "post_id_seq", name = "post_id_seq")
    @GeneratedValue(generator = "post_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /** The post author. */
    @Column(name = "author", nullable = false)
    private String author;

    /** The post content. */
    @NaturalId
    @Column(name = "title", nullable = false)
    private String title;

    /** The post content. */
    @NaturalId
    @Column(name = "content", nullable = false, length = 4000)
    private String content;

    protected Post() {
    }

    public Post(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post[author='" + this.getAuthor() + "', title='" + this.getTitle() + "',content='" + this.getContent() + "']";
    }
}
