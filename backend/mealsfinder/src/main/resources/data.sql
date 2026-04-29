INSERT INTO users (id, user_type, email, phone_number, username, password, is_account_confirmed, bio,
                   confirmation_code_expires_at, confirmation_code_created_at)
VALUES ('user-client-carlos', 'client', 'carlos.santos@email.com', '16991112222', 'carlos_santos', 'senha_hash_123', 1,
        'Amante de boa comida e fotografia.', NULL, NULL),
       ('user-client-ana', 'client', 'ana.oliveira@email.com', '16992223333', 'ana_oliveira', 'senha_hash_456', 1,
        'Explorando os melhores cafés da cidade.', NULL, NULL),
       ('user-client-bruno', 'client', 'bruno.costa@email.com', '16993334444', 'bruno_costa', 'senha_hash_789', 1,
        'Sempre em busca do sushi perfeito.', NULL, NULL),
       ('user-est-pizzaria', 'establishment', 'contato@bellapizza.com', '1633445566', 'bellapizza', 'senha_hash_est1',
        1, 'A melhor pizza de fermentação natural da cidade desde 2010.', NULL, NULL),
       ('user-est-sushi', 'establishment', 'atendimento@sushihouse.com', '1633556677', 'sushihouse', 'senha_hash_est2',
        1, 'Tradição e qualidade em cada peça. Venha nos conhecer!', NULL, NULL),
       ('user-est-cafe', 'establishment', 'faleconosco@graocultura.com', '1633667788', 'graocultura', 'senha_hash_est3',
        1, 'Cafés especiais e um ambiente perfeito para trabalhar e relaxar.', NULL, NULL);

INSERT INTO clients (user_id)
VALUES ('user-client-carlos'),
       ('user-client-ana'),
       ('user-client-bruno');

INSERT INTO establishments (user_id, cnpj, name, establishment_type, status, is_delivery, is_presencial,
                            address_city, address_state)
VALUES ('user-est-pizzaria', '11222333000144', 'Bella Pizza', 'Pizzaria', 'PENDING', 1, 1, 'São Carlos', 'SP'),
       ('user-est-sushi', '44555666000177', 'Sushi House', 'RESTAURANTE', 'PENDING', 1, 1, 'São Carlos', 'SP'),
       ('user-est-cafe', '77888999000122', 'Grão & Cultura Café', 'Cafeteria', 'PENDING', 0, 1, 'São Carlos', 'SP');

INSERT INTO reviews (id, user_id, description, reviewed_establishment_id, price_rate, food_rate, establishment_rate,
                     service_rate, is_delivery_review)
VALUES ('post-01-pizzaria', 'user-est-pizzaria',
        'Fim de semana chegou! Que tal uma Bella Pizza para comemorar? Peça pelo nosso delivery!',
        NULL, NULL, NULL, NULL, NULL, 0),
       ('post-02-sushi', 'user-est-sushi',
        'Peixe fresco todos os dias. A qualidade que você merece está aqui no Sushi House.',
        NULL, NULL, NULL, NULL, NULL, 0),
       ('post-03-cafe', 'user-est-cafe',
        'Nosso cantinho especial esperando por você. Perfeito para uma pausa no seu dia.',
        NULL, NULL, NULL, NULL, NULL, 0),
       ('post-04-carlos', 'user-client-carlos', 'Acabei de sair da Bella Pizza. Que experiência!',
        'user-est-pizzaria', 4.0, 5.0, 4.5, 4.0, 0),
       ('post-05-ana', 'user-client-ana', 'Trabalhando remotamente hoje do Grão & Cultura. Que lugar inspirador!',
        'user-est-cafe', 4.5, 5.0, 5.0, 5.0, 0),
       ('post-06-bruno', 'user-client-bruno', 'Noite de sushi com os amigos!',
        'user-est-sushi', 3.5, 4.5, 4.0, 4.0, 0);

INSERT INTO comments (id, review_id, user_id, parent_comment_id, description)
VALUES ('comment-01', 'post-01-pizzaria', 'user-client-carlos', NULL, 'A melhor da cidade, sem dúvidas!'),
       ('comment-02', 'post-01-pizzaria', 'user-client-ana', NULL, 'A de margherita é perfeita!'),
       ('comment-03', 'post-04-carlos', 'user-est-pizzaria', NULL, 'Que bom que gostou, Carlos! Volte sempre!'),
       ('comment-04', 'post-01-pizzaria', 'user-client-bruno', 'comment-01', 'Concordo plenamente!');

INSERT INTO images (id, review_id, url, image_type)
VALUES ('img-pizza-01', 'post-01-pizzaria', 'https://example.com/images/pizza_promo.jpg', 'POST');
INSERT INTO images (id, establishment_id, url, image_type)
VALUES ('img-cafe-ambiente', 'user-est-cafe', 'https://example.com/images/cafe_ambiente.jpg', 'ESTABLISHMENT_PICTURE'),
       ('img-sushi-fachada', 'user-est-sushi', 'https://example.com/images/sushi_fachada.jpg', 'ESTABLISHMENT_PICTURE'),
       ('img-menu-pizzaria-01', 'user-est-pizzaria', 'https://example.com/menus/pizzaria_pagina_1.jpg', 'MENU'),
       ('img-menu-pizzaria-02', 'user-est-pizzaria', 'https://example.com/menus/pizzaria_pagina_2.jpg', 'MENU'),
       ('img-menu-sushi-01', 'user-est-sushi', 'https://example.com/menus/sushi_completo.jpg', 'MENU'),
       ('img-menu-cafe-01', 'user-est-cafe', 'https://example.com/menus/cafe_bebidas.jpg', 'MENU'),
       ('img-menu-cafe-02', 'user-est-cafe', 'https://example.com/menus/cafe_comidas.jpg', 'MENU');

INSERT INTO comment_likes (user_id, comment_id)
VALUES ('user-client-ana', 'comment-01'),
       ('user-est-pizzaria', 'comment-01');

INSERT INTO follows (follower_id, following_id)
VALUES ('user-client-carlos', 'user-est-pizzaria'),
       ('user-client-carlos', 'user-est-sushi'),
       ('user-client-ana', 'user-est-cafe'),
       ('user-client-ana', 'user-client-carlos'),
       ('user-client-bruno', 'user-est-sushi');

INSERT INTO saved_reviews (user_id, review_id)
VALUES ('user-client-ana', 'post-02-sushi');

INSERT INTO client_liked_food_tags (client_id, food_tag)
VALUES ('user-client-carlos', 'PIZZA'),
       ('user-client-carlos', 'ITALIANA'),
       ('user-client-ana', 'CAFE'),
       ('user-client-ana', 'SOBREMESA'),
       ('user-client-bruno', 'SUSHI'),
       ('user-client-bruno', 'JAPONESA');

INSERT INTO establishment_food_tags (establishment_id, food_tag)
VALUES ('user-est-pizzaria', 'PIZZA'),
       ('user-est-pizzaria', 'ITALIANA'),
       ('user-est-pizzaria', 'VEGETARIANO'),
       ('user-est-sushi', 'SUSHI'),
       ('user-est-sushi', 'JAPONESA'),
       ('user-est-sushi', 'VEGANO'),
       ('user-est-cafe', 'CAFE'),
       ('user-est-cafe', 'VEGETARIANO');

INSERT INTO establishment_service_tags (establishment_id, service_tag)
VALUES ('user-est-pizzaria', 'DELIVERY_RAPIDO'),
       ('user-est-pizzaria', 'BOM_ATENDIMENTO'),
       ('user-est-sushi', 'ACEITA_RESERVAS'),
       ('user-est-sushi', 'BOM_ATENDIMENTO'),
       ('user-est-cafe', 'BOM_PARA_GRUPOS'),
       ('user-est-cafe', 'BOM_ATENDIMENTO');

INSERT INTO establishment_environment_tags (establishment_id, environment_tag)
VALUES ('user-est-pizzaria', 'FAMILIAR'),
       ('user-est-pizzaria', 'LEGAL_COM_AMIGOS'),
       ('user-est-sushi', 'MODERNO'),
       ('user-est-sushi', 'ROMANTICO'),
       ('user-est-sushi', 'WIFI'),
       ('user-est-cafe', 'ACONCHEGANTE'),
       ('user-est-cafe', 'AR_LIVRE'),
       ('user-est-cafe', 'WIFI');

INSERT INTO review_food_tags (review_id, food_tag)
VALUES ('post-04-carlos', 'PIZZA'),
       ('post-06-bruno', 'SUSHI');
