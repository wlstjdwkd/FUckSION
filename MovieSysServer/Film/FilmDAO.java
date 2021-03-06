package MovieSysServer.Film;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class FilmDAO {
	boolean insertResult = false;
	boolean deleteResult = false;

	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private ResultSet rs = null;

	public boolean insertFilm(FilmDTO dto) { //영화 추가

		String SQL = "INSERT INTO FILMS(FILM_NAME,FILM_TEASER,FILM_INFO,FILM_GENRE,FILM_OPENINGDATE,FILM_SUMMARY,FILM_POSTER)" + "VALUES (?,?,?,?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getFilm_name());
			pstmt.setString(2, dto.getFilm_teaser());
			pstmt.setString(3, dto.getFilm_info());
			pstmt.setString(4, dto.getFilm_genre());
			pstmt.setString(5, dto.getFilm_openingDate());
			pstmt.setString(6, dto.getFilm_summary());
			pstmt.setByte(7, dto.getFilm_poster());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}

	public ArrayList<FilmDTO> displayMovie() {  //영화 조회
		ArrayList<FilmDTO> arr = new ArrayList<FilmDTO>();
		FilmDTO dto = new FilmDTO();
		String SQL = "SELECT * FROM FILMS WHERE (FILM_OPENINGDATE<SYSDATE) AND (FILM_OPENINGDATE+30>SYSDATE) ORDER BY FILM_OPENINGDATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setFilm_name(rs.getString("FILM_NAME"));
				dto.setFilm_teaser(rs.getString("FILM_TEASER"));
				dto.setFilm_info(rs.getString("FILM_INFO"));
				dto.setFilm_genre(rs.getString("FILM_GENRE"));
				dto.setFilm_openingDate(rs.getString("FILM_OPENINGDATE"));
				dto.setFilm_summary(rs.getString("FILM_SUMMARY"));
				dto.setFilm_poster(rs.getByte("FILM_POSTER"));
				arr.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}
	
	public boolean deleteFilm(String title) {  //영화 삭제
		String SQL = "DELETE FROM FILMS WHERE FILM_NAME = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, title);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외 발생");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		deleteResult = true;
		return deleteResult;
	}
	
	public boolean insertReview(ReviewDTO dto) {  //리뷰 추가

		String SQL = "INSERT INTO REVIEWS(CUS_ID,FILM_ID,REV_STARPOINT,REV_CONTENT,REV_DATE)" + "VALUES (?,?,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dto.getCus_id());
			pstmt.setString(2, dto.getFilm_id());
			pstmt.setInt(3, dto.getRev_starPoint());
			pstmt.setString(4, dto.getRev_content());
			pstmt.setString(5, dto.getRev_date());
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("INSERT문에서 예외 발생");
			sqle.printStackTrace();
			insertResult = false;
			return insertResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		insertResult = true;
		return insertResult;
	}

	public ArrayList<ReviewDTO> displayReview(String movid) {  //리뷰 조회

		ArrayList<ReviewDTO> arr = new ArrayList<ReviewDTO>();
		ReviewDTO dto = new ReviewDTO();
		String SQL = "SELECT * FROM REVIEWS WHERE FILM_ID=" + movid + " ORDER BY REV_DATE DESC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setCus_id(rs.getString("CUS_ID"));
				dto.setFilm_id(rs.getString("FILM_ID"));
				dto.setRev_date(rs.getString("REV_DATE"));
				dto.setRev_content(rs.getString("REV_CONTENT"));
				dto.setRev_starPoint(rs.getInt("REV_rev_STARPOINT"));
				arr.add(dto);
			}
		} catch (SQLException sqle) {
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			if(rs!=null) try{rs.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		return arr;
	}

	public boolean deleteReview(String id) {  //리뷰 삭제
		String SQL = "DELETE FROM REVIEWS WHERE REV_ID = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("DELETE문에서 예외 발생");
			sqle.printStackTrace();
			deleteResult = false;
			return deleteResult;
		} finally {
			if(pstmt!=null) try{pstmt.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
			if(conn!=null) try{conn.close();} catch(Exception e){throw new RuntimeException(e.getMessage());}
		}
		deleteResult = true;
		return deleteResult;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "movieAdmin";
			String pw = "movieadmin";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
}
