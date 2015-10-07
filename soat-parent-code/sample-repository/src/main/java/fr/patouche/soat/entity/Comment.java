package fr.patouche.soat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Comment entity.
 *
 * @author patouche - 06/10/15
 */
@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Technical id. */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** The comment content. */
    @Column(name = "CONTENT", nullable = false, length = 4000)
    private String content;

    /** The author name. */
    @Column(name = "USER_ID", nullable = false)
    private String author;

    /** The post which the comment is related. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "POST_ID")
    private Post post;

    protected Comment() {
    }

    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(final Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Post[author='" + this.getAuthor() + "', content='" + this.getContent() + "', post='" + this.getPost() + "']";
    }
}
