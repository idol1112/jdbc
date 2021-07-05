package com.javaex.ex05;

import java.util.List;
import java.util.Scanner;

public class BookApp {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		AuthorDao authorDao = new AuthorDao();
		List<AuthorVo> authorList;

		BookDao bookDao = new BookDao();
		List<BookVo> bookList;
		List<BookVo> bookSearchList;

		// 작가테이블 책테이블 완성 --> 내용은 안 넣기
		// 작가테이블 시퀀스, 책테이블 시퀀스 완성
		authorDao.authorTable();
		authorDao.authorSeq();

		bookDao.bookTable();
		bookDao.bookSeq();

		// authorDao.authorInsert(); 이용해서 데이터 추가 --> 여기서 내용 추가, 작가 6명
		AuthorVo iAuthorVo_01 = new AuthorVo("이문열", "경북 영양");
		authorDao.authorInsert(iAuthorVo_01);

		AuthorVo iAuthorVo_02 = new AuthorVo("박경리", "경상남도 통영");
		authorDao.authorInsert(iAuthorVo_02);

		AuthorVo iAuthorVo_03 = new AuthorVo("이고잉", "가명");
		authorDao.authorInsert(iAuthorVo_03);

		AuthorVo iAuthorVo_04 = new AuthorVo("기안84", "기안동에서 산 84년생");
		authorDao.authorInsert(iAuthorVo_04);

		AuthorVo iAuthorVo_05 = new AuthorVo("강풀", "온라인 만화가 1세대");
		authorDao.authorInsert(iAuthorVo_05);

		AuthorVo iAuthorVo_06 = new AuthorVo("김영하", "알쓸신잡");
		authorDao.authorInsert(iAuthorVo_06);

		// bookDao.bookInsert(); 책 8개 추가
		BookVo iBookVo_01 = new BookVo("우리들의 일그러진 영웅", "다림", "1998-02-22", 1);
		bookDao.bookInsert(iBookVo_01);

		BookVo iBookVo_02 = new BookVo("삼국지", "민음사", "2002-03-01", 1);
		bookDao.bookInsert(iBookVo_02);

		BookVo iBookVo_03 = new BookVo("토지", "마로니에북스", "2012-08-15", 2);
		bookDao.bookInsert(iBookVo_03);

		BookVo iBookVo_04 = new BookVo("자바프로그래밍 입문", "위키북스", "2015-04-01", 3);
		bookDao.bookInsert(iBookVo_04);

		BookVo iBookVo_05 = new BookVo("패션왕", "중앙북스(books)", "2012-02-22", 4);
		bookDao.bookInsert(iBookVo_05);

		BookVo iBookVo_06 = new BookVo("순정만화", "재미주의", "2011-08-03", 5);
		bookDao.bookInsert(iBookVo_06);

		BookVo iBookVo_07 = new BookVo("오직두사람", "문학동네", "2017-05-04", 6);
		bookDao.bookInsert(iBookVo_07);

		BookVo iBookVo_08 = new BookVo("26년", "재미주의", "2012-02-04", 5);
		bookDao.bookInsert(iBookVo_08);

		// 리스트출력
		// DB에서 가져오기
		bookList = bookDao.getBookList();
		// 리스트를 for문으로 출력 --> 메소드로 정의
		printList(bookList);

		/*****************************
		 * 수정&삭제 테스트 //수정 테스트 AuthorVo uAuthorVo = new AuthorVo(7, "김덕배", "50만 유튜버");
		 * authorDao.authorUpdate(uAuthorVo);
		 * 
		 * BookVo uBookVo = new BookVo("삶의 현장 르포", "이순재", "2021-05-05", 4, 5);
		 * bookDao.bookUpdate(uBookVo);
		 * 
		 * //책리스트 출력 //bookDao.getBookList(); --> 8개
		 * 
		 * //리스트출력 //DB에서 가져오기 bookList = bookDao.getBookList(); //리스트를 for문으로 출력 -->
		 * 메소드로 정의 printList(bookList);
		 * 
		 * 
		 * 
		 * 
		 * //삭제 테스트 int dCount = authorDao.authorDelete(1);
		 * 
		 * //DB에서 가져오기 bookList = bookDao.getBookList(); //리스트를 for문으로 출력 --> 메소드로 정의
		 * printList(bookList);
		 * 
		 ********************************/

		/*************** 위에 다 한 사람 추가로 하기 **************/
		// 스캐너를 통해서 사용자한테 키워드 입력받음
		// "검색을 입력해주세요"
		// "검색어: 문"

		// bookDao.getBookList(keyword); --> 4개
		// 책 정보 출력(문이 들어간)
		System.out.println("검색을 입력해주세요");
		System.out.print("검색어: ");
		String keyword = sc.nextLine();

		bookSearchList = bookDao.getBookList(keyword);
		printList(bookSearchList);

		/*
		 * BookDao의 메소드에서 스캐너 사용하는 방법 bookSearchList = bookDao.bookSearch(); //리스트를
		 * for문으로 출력 --> 메소드로 정의 printList(bookSearchList);
		 */

		sc.close();

	}

	public static void printList(List<BookVo> bookList) {

		for (int i = 0; i < bookList.size(); i++) {

			BookVo bookVo = bookList.get(i);
			System.out.println(bookVo.getBookId() + "\t" + bookVo.getTitle() + "\t" + bookVo.getPubs() + "\t"
					+ bookVo.getPubDate() + "\t" + bookVo.getAuthorId() + "\t" + bookVo.getAuthorName() + "\t"
					+ bookVo.getAuthorDesc());

		}

		System.out.println("===============================");
		System.out.println("");

	}

}
