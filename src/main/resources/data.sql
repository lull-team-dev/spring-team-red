INSERT INTO users (name, address, tel, email, password) VALUES
('田中 太郎', '東京都新宿区1-1-1', '090-1234-5678', 'tanaka@example.com', 'pass123'),
('鈴木 花子', '大阪府大阪市北区2-2-2', '080-2345-6789', 'suzuki@example.com', 'hanako456'),
('佐藤 次郎', '愛知県名古屋市中区3-3-3', '070-3456-7890', 'sato@example.com', 'jiro789'),
('高橋 美咲', '福岡県福岡市博多区4-4-4', '090-4567-8901', 'takahashi@example.com', 'misaki321'),
('伊藤 健', '北海道札幌市中央区5-5-5', '080-5678-9012', 'ito@example.com', 'ken999');

INSERT INTO hotels (name, photo, prefecture, city) VALUES
('富士見荘', '/images/fujimi.jpg', '山梨県', '富士吉田市'),
('海風館', '/images/umikaze.jpg', '神奈川県', '鎌倉市'),
('森の宿り', '/images/morinoyado.jpg', '長野県', '軽井沢町'),
('雪月花亭', '/images/setsugetsuka.jpg', '北海道', '札幌市'),
('はなみずき旅館', '/images/hanamizuki.jpg', '京都府', '京都市'),
('陽だまり庵', '/images/hidamari.jpg', '大分県', '由布市');

INSERT INTO plans (hotel_id, name, price, description, plan_image) VALUES
(1, '富士見絶景プラン', 15000, '露天風呂から富士山を一望できるプランです', '/images/fuji_plan.jpg'),
(1, 'ゆったり朝食付きプラン', 12000, '地元食材を使った朝食付きのお得なプラン', '/images/breakfast_plan.jpg'),
(2, '海の幸堪能プラン', 20000, '新鮮な海鮮料理が楽しめる2食付きプラン', '/images/seafood_plan.jpg'),
(2, '記念日特別プラン',  18000,'スパークリングワインとケーキ付きの記念日プラン', '/images/anniversary_plan.jpg'),
(3, '貸切温泉プラン', 8000, '家族・カップルに人気の貸切露天風呂付き', '/images/private_onsen_plan.jpg'),
(3, '平日限定割引プラン',  11000,'平日限定で宿泊料金20%OFFのプラン', '/images/weekday_discount.jpg');