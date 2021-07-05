package com.javaex.ex05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자

	// 메소드-gs

	// 메소드-일반

	// DB연결
	public void getConnection() {

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 자원정리
	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 북 테이블 만들기
	public void bookTable() {

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " create table book( ";
			query += "     book_id number(5), ";
			query += "     title varchar2(50), ";
			query += "     pubs varchar2(50), ";
			query += "     pub_date date, ";
			query += "     author_id number(5), ";
			query += "     primary key(book_id), ";
			query += "     constraint book_fk foreign key(author_id)  ";
			query += "     references author(author_id) ";
			query += "	   ON DELETE CASCADE ";
			query += " ) ";

			pstmt = conn.prepareStatement(query);

			pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("북테이블 생성");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

	}

	// 시퀀스 생성
	public void bookSeq() {

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " create sequence seq_book_id ";
			query += " increment by 1 ";
			query += " start with 1 ";
			query += " nocache ";

			pstmt = conn.prepareStatement(query);

			// 4.결과처리
			System.out.println("북테이블 시퀀스 생성");

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();
	}

	// 책 삭제하기
	public int bookDelete(int bookId) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

		return count;
	}

	// 북수정
	public int bookUpdate(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update book ";
			query += " set title = ?, ";
			query += "     pubs = ?, ";
			query += "     pub_date = ? , ";
			query += "     author_id = ? ";
			query += " where book_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

		return count;

	}

	// 북 등록
	public int bookInsert(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into book ";
			query += " values(seq_book_id.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.close();

		return count;

	}

	// 북 리스트 가져오기
	public List<BookVo> getBookList() {

		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select  b.book_id, ";
			query += "         b.title, ";
			query += "         b.pubs, ";
			query += "         to_char(b.pub_date, 'yyyy-mm-dd') pubDate, ";
			query += "         a.author_id, ";
			query += "         a.author_name, ";
			query += "         a.author_desc ";
			query += " from book b, author a ";
			query += " where b.author_id = a.author_id ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String bookTitle = rs.getString("title");
				String bookPubs = rs.getString("pubs");
				String bookPubdate = rs.getString("pubDate");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				BookVo bookVo = new BookVo(bookId, bookTitle, bookPubs, bookPubdate, authorId, authorName, authorDesc);

				bookList.add(bookVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;

	}

	/*
	 * BookDao의 메소드에서 스캐너 사용하는 방법 //검색하기 public List<BookVo> bookSearch() {
	 * 
	 * List<BookVo> bookSearch = new ArrayList<BookVo>();
	 * 
	 * Scanner sc = new Scanner(System.in); this.getConnection();
	 * 
	 * try {
	 * 
	 * System.out.println("검색을 입력해주세요"); System.out.print("검색어: "); String search =
	 * sc.nextLine();
	 * 
	 * // 3. SQL문 준비 / 바인딩 / 실행 String query = ""; query += " select  b.book_id, ";
	 * query += "         b.title, "; query += "         b.pubs, "; query +=
	 * "         to_char(b.pub_date, 'yyyy-mm-dd') pubDate, "; query +=
	 * "         a.author_id, "; query += "         a.author_name, "; query +=
	 * "         a.author_desc "; query += " from book b, author a "; query +=
	 * " where b.author_id = a.author_id "; query +=
	 * " and (b.pubs || b.title || a.author_name) like ";
	 * 
	 * pstmt = conn.prepareStatement(query + "'%" + search + "%'");
	 * 
	 * pstmt.executeUpdate();
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * // 4.결과처리 while(rs.next()) { int bookId = rs.getInt("book_id"); String
	 * bookTitle = rs.getString("title"); String bookPubs = rs.getString("pubs");
	 * String bookPubdate = rs.getString("pubDate"); int authorId =
	 * rs.getInt("author_id"); String authorName = rs.getString("author_name");
	 * String authorDesc = rs.getString("author_desc");
	 * 
	 * BookVo bookVo = new BookVo(bookId, bookTitle, bookPubs, bookPubdate,
	 * authorId, authorName, authorDesc);
	 * 
	 * bookSearch.add(bookVo);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * sc.close(); this.close();
	 * 
	 * return bookSearch; }
	 */

	// 검색하기
	public List<BookVo> getBookList(String keyword) {
				
				List<BookVo> bookSearchList = new ArrayList<BookVo>();

				this.getConnection();
				
				try {
					
					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "";
					query += " select  b.book_id, ";
					query += "         b.title, ";
					query += "         b.pubs, ";
					query += "         to_char(b.pub_date, 'yyyy-mm-dd') pubDate, ";
					query += "         a.author_id, ";
					query += "         a.author_name, ";
					query += "         a.author_desc ";
					query += " from book b, author a ";
					query += " where b.author_id = a.author_id ";
					query += " and (b.pubs || b.title || a.author_name) like ";
					
					pstmt = conn.prepareStatement(query + "'%" + keyword + "%'");
					
					pstmt.executeUpdate();
					
					rs = pstmt.executeQuery();
					
					// 4.결과처리
					while(rs.next()) {
						int bookId = rs.getInt("book_id");
						String bookTitle = rs.getString("title");
						String bookPubs = rs.getString("pubs");
						String bookPubdate = rs.getString("pubDate");
						int authorId = rs.getInt("author_id");
						String authorName = rs.getString("author_name");
						String authorDesc = rs.getString("author_desc");
						
						BookVo bookVo = new BookVo(bookId, bookTitle, bookPubs, bookPubdate, authorId, authorName, authorDesc);
						
						bookSearchList.add(bookVo);
						
					}		
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				this.close();
				
				return bookSearchList;

	}
}
