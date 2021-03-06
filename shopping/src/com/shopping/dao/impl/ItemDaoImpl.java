package com.shopping.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shopping.dao.ItemDao;
import com.shopping.util.DBUtil;
import com.shopping.vo.ItemVo;

public class ItemDaoImpl implements ItemDao {

	public boolean addItem(ItemVo item) {
		boolean flag = false;
		String sql = "INSERT INTO item (cat_id, item_name, item_desc, item_datetime) VALUES (?,?,?,NOW())";
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setInt(1, item.getCatId());
			pstmt.setString(2, item.getItemName());
			pstmt.setString(3, item.getItemDesc());
			if (pstmt.executeUpdate() == 1) {
				flag = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return flag;
	}

	public List<ItemVo> findAllItem(int categoryId, int start, int limit) {
		List<ItemVo> list = new ArrayList<ItemVo>();
		String sql = "SELECT * FROM item WHERE cat_id = ? and 1 = 1 LIMIT "
				+ start + "," + limit;
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVo item = new ItemVo();
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("cat_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
				list.add(item);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;
	}

	public List<ItemVo> findAllItem(int start, int limit) {
		List<ItemVo> list = new ArrayList<ItemVo>();
		String sql = "SELECT * FROM item WHERE 1 = 1 LIMIT " + start + ","
				+ limit;
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVo item = new ItemVo();
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("cat_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
				list.add(item);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;
	}

	public ItemVo findById(int itemId) {
		ItemVo item = new ItemVo();
		String sql = "SELECT * FROM item WHERE item_id = ? AND 1 = 1";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setInt(1, itemId);
			rs = pst.executeQuery();
			while (rs.next()) {
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("item_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}

		return item;
	}

	public boolean modifyItem(ItemVo item) {
		boolean flag = false;
		String sql = "UPDATE item SET cat_id = ?, item_name = ?, item_desc = ?"
				+ " WHERE item_id = ?";
		PreparedStatement pst = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setInt(1, item.getCatId());
			pst.setString(2, item.getItemName());
			pst.setString(3, item.getItemDesc());
			pst.setInt(4, item.getItemId());
			if (pst.executeUpdate() == 0 || pst.executeUpdate() == 1) {
				flag = true;
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return flag;
	}

	public boolean removeItemById(int itemId) {
		Boolean flag = false;
		String sql = "DELETE FROM item WHERE item_id = ?";
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setInt(1, itemId);
			if (pstmt.executeUpdate() == 1) {
				flag = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return flag;
	}

	public List<ItemVo> findItemByCategoryId(int catId) {
		List<ItemVo> list = new ArrayList<ItemVo>();
		String sql = "SELECT * FROM item WHERE cat_id = ? ";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setInt(1, catId);
			rs = pst.executeQuery();
			while (rs.next()) {
				ItemVo item = new ItemVo();
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("cat_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
				list.add(item);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;
	}

	public int getTotalNum() {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM item";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return total;
	}

	public int getTotalNum(String name) {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM item WHERE item_name LIKE ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return total;
	}

	public List<ItemVo> findItemByLike(String name, int start, int limit) {
		List<ItemVo> list = new ArrayList<ItemVo>();
		String sql = "SELECT * FROM item WHERE item_name LIKE ? LIMIT " + start
				+ "," + limit;
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVo item = new ItemVo();
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("cat_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
				list.add(item);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;
	}

	public int getTotalNum(int id) {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM item WHERE cat_id = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return total;
	}

	public List<ItemVo> findAllItem() {
		List<ItemVo> list = new ArrayList<ItemVo>();
		String sql = "SELECT * FROM item WHERE 1 = 1";
		PreparedStatement pstmt = null;
		DBUtil dbc = new DBUtil();
		try {
			pstmt = dbc.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVo item = new ItemVo();
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("cat_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
				list.add(item);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}
		return list;
	}

	public ItemVo findItemByName(String name) {
		ItemVo item = new ItemVo();
		String sql = "SELECT * FROM item WHERE item_name = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBUtil dbc = new DBUtil();
		try {
			pst = dbc.getConnection().prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while (rs.next()) {
				item.setItemId(rs.getInt("item_id"));
				item.setCatId(rs.getInt("item_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDesc(rs.getString("item_desc"));
				item.setItemDatetime(rs.getString("item_datetime").substring(0,
						19));
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.close();
		}

		return item;
	}

}
