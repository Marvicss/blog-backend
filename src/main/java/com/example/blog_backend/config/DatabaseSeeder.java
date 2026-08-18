package com.example.blog_backend.config;

import com.example.blog_backend.modules.post.Post;
import com.example.blog_backend.modules.post.PostRepository;
import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.UserRepository;
import com.example.blog_backend.modules.user.enums.UserEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("Banco de dados já possui usuários. Pulando semeação de dados.");
            return;
        }

        System.out.println("Semeando banco de dados com dados de teste...");

        // 1. Criar Usuários
        User admin = new User();
        admin.setName("Admin Marconi");
        admin.setEmail("admin@blog.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole(UserEnum.ADMIN);
        userRepository.save(admin);

        User author1 = new User();
        author1.setName("Alice Autora");
        author1.setEmail("alice@blog.com");
        author1.setPassword(passwordEncoder.encode("password123"));
        author1.setRole(UserEnum.AUTHOR);
        userRepository.save(author1);

        User author2 = new User();
        author2.setName("Bob Autor");
        author2.setEmail("bob@blog.com");
        author2.setPassword(passwordEncoder.encode("password123"));
        author2.setRole(UserEnum.AUTHOR);
        userRepository.save(author2);

        User reader = new User();
        reader.setName("Lucas Leitor");
        reader.setEmail("lucas@blog.com");
        reader.setPassword(passwordEncoder.encode("password123"));
        reader.setRole(UserEnum.READER);
        userRepository.save(reader);

        // 2. Criar Posts
        createPost(
            "Introdução ao Angular 21",
            "Aprenda os conceitos fundamentais do Angular, o framework do Google para desenvolvimento web.",
            "O Angular é um framework completo para desenvolvimento de aplicações SPA (Single Page Applications). Nesta introdução, exploramos o uso de components standalone, signals para controle reativo de estado e injeção de dependências moderna com a função inject.\n\n" +
            "Para começar um projeto, você pode usar o comando 'npx -y @angular/cli new nome-do-projeto'.",
            author1,
            Arrays.asList("angular", "frontend", "typescript"),
            "Frontend",
            "introducao-ao-angular-21",
            PostStatusEnum.PUBLISHED,
            BigInteger.valueOf(142)
        );

        createPost(
            "Construindo APIs Robustas com Spring Boot",
            "Descubra as melhores práticas para desenvolver APIs REST rápidas, escaláveis e seguras usando Java e Spring.",
            "O ecossistema Spring Boot facilita a criação de microsserviços e aplicações web corporativas. Discutimos aqui os pilares das APIs REST: injeção de dependência via construtores, validação de inputs com @Valid, tratamento global de exceções usando @ControllerAdvice e controle de acesso preciso usando Spring Security.",
            author2,
            Arrays.asList("java", "spring-boot", "backend"),
            "Backend",
            "construindo-apis-robustas-com-spring-boot",
            PostStatusEnum.PUBLISHED,
            BigInteger.valueOf(98)
        );

        createPost(
            "Guia Completo de Docker para Desenvolvedores",
            "Entenda como conteinerizar sua aplicação e simplificar o fluxo de desenvolvimento local e deploy.",
            "Docker revolucionou a forma como empacotamos e executamos nossas aplicações. Com o uso de imagens leves, Dockerfiles bem estruturados e Docker Compose para gerenciar múltiplos contêineres locais (como bancos de dados Postgres e caches Redis), eliminamos o clássico problema do 'funciona na minha máquina'.",
            admin,
            Arrays.asList("docker", "devops", "containers"),
            "DevOps",
            "guia-completo-de-docker-para-desenvolvedores",
            PostStatusEnum.PUBLISHED,
            BigInteger.valueOf(255)
        );

        createPost(
            "Novo Artigo em Rascunho",
            "Este é um artigo do Bob que ainda não foi publicado.",
            "Este artigo trata-se de ideias iniciais sobre arquitetura limpa (Clean Architecture) com DDD em projetos de software. Apenas Bob e administradores devem conseguir visualizar este texto nos filtros da listagem do blog.",
            author2,
            Arrays.asList("arquitetura", "ddd"),
            "Design Patterns",
            "novo-artigo-em-rascunho",
            PostStatusEnum.DRAFT,
            BigInteger.ZERO
        );

        createPost(
            "Artigo Arquivado sobre AngularJS",
            "Um breve retrospecto sobre a versão antiga do framework do Google.",
            "Este conteúdo está arquivado e preserva discussões antigas sobre o AngularJS (versão 1.x).",
            author1,
            Arrays.asList("angularjs", "legacy"),
            "Frontend",
            "artigo-arquivado-sobre-angularjs",
            PostStatusEnum.ARCHIVED,
            BigInteger.valueOf(45)
        );

        createPost(
            "Dominando o Redis para Caching",
            "Melhore a performance de suas consultas pesadas de banco utilizando estratégias eficientes de cache com Redis.",
            "Consultas repetitivas a bancos de dados relacionais podem gargalar sua aplicação. Com o Redis, armazenamos em memória dados estáticos e consultas frequentes (como o feed principal do blog), diminuindo o tempo de resposta das requisições para milissegundos.",
            admin,
            Arrays.asList("redis", "cache", "performance"),
            "Database",
            "dominando-o-redis-para-caching",
            PostStatusEnum.PUBLISHED,
            BigInteger.valueOf(189)
        );

        System.out.println("Banco de dados semeado com sucesso!");
    }

    private void createPost(String title, String shortDesc, String content, User author, List<String> tags, String category, String slug, PostStatusEnum status, BigInteger views) {
        Post post = new Post();
        post.setTitle(title);
        post.setShortDescription(shortDesc);
        post.setContent(content);
        post.setAuthor(author);
        post.setTags(tags);
        post.setCategory(category);
        post.setSlug(slug);
        post.setStatus(status);
        post.setViews(views);
        postRepository.save(post);
    }
}
