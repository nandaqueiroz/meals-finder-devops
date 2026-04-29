INSERT INTO environment_tags (name)
VALUES ('Aconchegante'),
       ('Moderno'),
       ('Romântico'),
       ('Familiar'),
       ('Ar livre'),
       ('Agitado');

INSERT INTO food_tags (name)
VALUES ('Pizza'),
       ('Sushi'),
       ('Hambúrguer'),
       ('Café'),
       ('Italiana'),
       ('Japonesa'),
       ('Sobremesa'),
       ('Vegano');

INSERT INTO service_tags (name)
VALUES ('Delivery Rápido'),
       ('Bom para Grupos'),
       ('Aceita Reservas'),
       ('Música ao Vivo');

-- Users com IDs numéricos sequenciais (1..6)
-- 1 carlos | 2 ana | 3 bruno | 4 pizzaria | 5 sushi | 6 cafe
INSERT INTO users (id, user_type, email, phone_number, username, password, is_account_confirmed, bio)
VALUES ('1', 'client', 'carlos.santos@email.com', '16991112222', 'carlos_santos', 'senha_hash_123', 1,
        'Amante de boa comida e fotografia.'),
       ('2', 'client', 'ana.oliveira@email.com', '16992223333', 'ana_oliveira', 'senha_hash_456', 1,
        'Explorando os melhores cafés da cidade.'),
       ('3', 'client', 'bruno.costa@email.com', '16993334444', 'bruno_costa', 'senha_hash_789', 1,
        'Sempre em busca do sushi perfeito.'),
       ('4', 'establishment', 'contato@bellapizza.com', '1633445566', 'bellapizza', 'senha_hash_est1',
        1, 'A melhor pizza de fermentação natural da cidade desde 2010.'),
       ('5', 'establishment', 'atendimento@sushihouse.com', '1633556677', 'sushihouse', 'senha_hash_est2',
        1, 'Tradição e qualidade em cada peça. Venha nos conhecer!'),
       ('6', 'establishment', 'faleconosco@graocultura.com', '1633667788', 'graocultura', 'senha_hash_est3',
        1, 'Cafés especiais e um ambiente perfeito para trabalhar e relaxar.');

INSERT INTO clients (user_id)
VALUES ('1'),
       ('2'),
       ('3');

INSERT INTO establishments (user_id, cnpj, name, establishment_type, status, is_delivery, is_presencial)
VALUES ('4', '11222333000144', 'Bella Pizza', 'Pizzaria', 'PENDING', 1, 1),
       ('5', '44555666000177', 'Sushi House', 'RESTAURANTE', 'PENDING', 1, 1),
       ('6', '77888999000122', 'Grão & Cultura Café', 'Cafeteria', 'PENDING', 0, 1);

-- Posts: ids 1..6 (1..3 dos estabelecimentos, 4..6 dos clientes)
INSERT INTO posts (id, user_id, description)
VALUES ('1', '4',
        'Fim de semana chegou! Que tal uma Bella Pizza para comemorar? Peça pelo nosso delivery!'),
       ('2', '5',
        'Peixe fresco todos os dias. A qualidade que você merece está aqui no Sushi House.'),
       ('3', '6',
        'Nosso cantinho especial esperando por você. Perfeito para uma pausa no seu dia.'),
       ('4', '1', 'Acabei de sair da Bella Pizza. Que experiência!'),
       ('5', '2', 'Trabalhando remotamente hoje do Grão & Cultura. Que lugar inspirador!'),
       ('6', '3', 'Noite de sushi com os amigos!');

INSERT INTO reviews (post_id, reviewed_establishment_id, food_rate, service_rate, establishment_rate, price_rate)
VALUES ('4', '4', 5.0, 4.0, 4.5, 4.0),
       ('5', '6', 5.0, 5.0, 5.0, 4.5),
       ('6', '5', 4.5, 4.0, 4.0, 3.5);

-- Comments: ids 1..4
INSERT INTO comments (id, post_id, user_id, description)
VALUES ('1', '1', '1', 'A melhor da cidade, sem dúvidas!'),
       ('2', '1', '2', 'A de margherita é perfeita!'),
       ('3', '4', '4', 'Que bom que gostou, Carlos! Volte sempre!');

-- Resposta a comentário usa post_id do post original e parent_comment_id
INSERT INTO comments (id, post_id, user_id, parent_comment_id, description)
VALUES ('4', '1', '3', '1', 'Concordo plenamente!');

INSERT INTO images (id, post_id, url, image_type)
VALUES ('1', '1', 'https://example.com/images/pizza_promo.jpg', 'POST');
INSERT INTO images (id, establishment_id, url, image_type)
VALUES ('2', '6', 'https://example.com/images/cafe_ambiente.jpg', 'ESTABLISHMENT_PICTURE'),
       ('3', '5', 'https://example.com/images/sushi_fachada.jpg', 'ESTABLISHMENT_PICTURE'),
       ('4', '4', 'https://example.com/menus/pizzaria_pagina_1.jpg', 'MENU'),
       ('5', '4', 'https://example.com/menus/pizzaria_pagina_2.jpg', 'MENU'),
       ('6', '5', 'https://example.com/menus/sushi_completo.jpg', 'MENU'),
       ('7', '6', 'https://example.com/menus/cafe_bebidas.jpg', 'MENU'),
       ('8', '6', 'https://example.com/menus/cafe_comidas.jpg', 'MENU');

INSERT INTO post_likes (user_id, post_id)
VALUES ('2', '1'),
       ('3', '1'),
       ('1', '2'),
       ('2', '4'),
       ('5', '4');

INSERT INTO comment_likes(user_id, comment_id)
VALUES ('2', '1'),
       ('4', '1');

INSERT INTO follows (follower_id, following_id)
VALUES ('1', '4'),
       ('1', '5'),
       ('2', '6'),
       ('2', '1'),
       ('3', '5');

INSERT INTO saved_posts (user_id, post_id)
VALUES ('2', '2');

INSERT INTO client_liked_food_tags (client_id, food_tag_id)
VALUES ('1', 1),
       ('1', 5),
       ('2', 4),
       ('2', 7),
       ('3', 2),
       ('3', 6);

INSERT INTO establishment_food_tags (establishment_id, food_tag_id)
VALUES ('4', 1),
       ('4', 5),
       ('5', 2),
       ('5', 6),
       ('6', 4);

INSERT INTO establishment_service_tags (establishment_id, service_tag_id)
VALUES ('4', 1),
       ('5', 3),
       ('6', 2);

INSERT INTO establishment_environment_tags (establishment_id, environment_tag_id)
VALUES ('4', 4),
       ('5', 2),
       ('5', 3),
       ('6', 1),
       ('6', 5);

INSERT INTO post_food_tags (post_id, food_tag_id)
VALUES ('4', 1),
       ('6', 2);

INSERT INTO user_activities (id, user_id, action_type, entity_type, entity_id)
VALUES ('1', '2', 'LIKE', 'POST', '1'),
       ('2', '3', 'LIKE', 'POST', '1'),
       ('3', '1', 'COMMENT', 'POST', '1'),
       ('4', '2', 'FOLLOW', 'USER', '6');
