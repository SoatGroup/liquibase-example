/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.patouche.soat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import org.hibernate.annotations.NaturalId;

/**
 * Post entity.
 *
 * @author patouche - 06/10/15
 */
@Entity
@Table(name = "POST")
public class Post implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Technical identifier. */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    /** The post author. */
    @Column(name = "AUTHOR")
    private String author;

    /** The post content. */
    @NaturalId
    @Column(nullable = false)
    private String content;

    protected Post() {
    }

    public Post(String author, String content) {
        this.author = author;
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

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post[author='" + this.getAuthor() + "', content='" + this.getContent() + "']";
    }
}
