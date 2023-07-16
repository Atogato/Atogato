use backend;
DROP TABLE SITEUSER CASCADE;

select * from artist_favorites;
select * from artists;
select * from hibernate_sequence;
select * from message;
select * from project;
select * from project_favorites;
select * from project_required_category;
select * from user;
select * from user_refresh_token;


INSERT INTO user(user_seq, created_at, email,  email_verified_yn, modified_at,
			password, profile_image_url, provider_type, role_type, user_id, username)
 VALUES('1', '2023-07-12 20:06:24.857855', 'rudgusee@gmail.com', 'Y', 
 '2023-07-12 20:06:24.857855', 'NO_PASS', 
 'https://lh3.googleusercontent.com/a/AAcHTtf1olptelTvYgrVQJVTqWpNgnrM5NY1oX6MC2xSWyY0=s96-c', 
 'GOOGLE', 'USER', '103000778898269467053', '김경현');

INSERT INTO user_refresh_token(refresh_token_seq, refresh_token, user_id)
 VALUES('1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MjZEOTZDOTAwMzBERDU4NDI5RDI3NTFBQzFCREJCQyIsImV4cCI6MTY4OTc2NDc4NH0.S1nHGpTSkKn5oTjEmcUe8jDGnSV4b_uImC7oSaovvIc', '103000778898269467053');

