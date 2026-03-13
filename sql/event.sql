CREATE TABLE event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tab VARCHAR(20) NOT NULL,         -- ongoing / ended 등
    title VARCHAR(255) NOT NULL,      -- 이벤트 제목
    img VARCHAR(255) NOT NULL,        -- 이미지 파일명
    alt VARCHAR(100),                  -- 이미지 alt
    date VARCHAR(50),                  -- 이벤트 기간
    content TEXT                       -- 상세 내용
);