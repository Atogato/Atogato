DROP TABLE SITEUSER CASCADE;

create table Hello(
	id INT NOT NULL AUTO_INCREMENT,
	CONSTRAINT Hello_PK PRIMARY KEY(id)
);

select * from hello;
select * from SITEUSER;
use backend;

CREATE TABLE SITEUSER (
	 id INT NOT NULL AUTO_INCREMENT,
	 userId VARCHAR(40) NOT NULL,
     username VARCHAR(40) NOT NULL , 
	 password  VARCHAR(200)  NOT NULL,
     artistType VARCHAR(20) NOT NULL , 
     user_type VARCHAR(10) DEFAULT 'USER',
     CONSTRAINT USER_ID_PK  PRIMARY KEY(ID),
     CONSTRAINT USER_USERNAME_UQ 	UNIQUE (USERNAME),
     CONSTRAINT USER_USERID_UQ 	UNIQUE (USERID),
     CONSTRAINT USER_ARTISTTYPE_CK CHECK (ARTISTTYPE IN ('연기', '노래', '제작', '춤', '작곡', '기타')),
     CONSTRAINT USER_USER_TYPE_CK CHECK (USERTYPE IN ('USER', 'ADMIN'))
);

INSERT INTO SITEUSER(userId, username, password, artistType)
 VALUES( 'kim@naver.com', 'rudgusee',  '1234', '연기');

INSERT INTO SITEUSER(userId, username, password, artistType)
 VALUES(  'hmson@naver.com', 'hmson', '5678', '노래');
