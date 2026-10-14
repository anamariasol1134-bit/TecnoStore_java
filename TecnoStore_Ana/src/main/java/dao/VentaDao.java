
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Celular;
import modelo.Cliente;
import modelo.Empleado;
import modelo.ItemVenta;
import modelo.Venta;
import patron.ConexionDB;
public class VentaDao {
    public void insertar(Venta v) {
        String sqlVenta = "INSERT INTO ventas (cliente_fk, empleado_fk, fecha, subtotal, iva, total) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlItem  = "INSERT INTO detalle_venta (venta_fk, celular_fk, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE celulares SET stock = stock - ? WHERE id = ?";
        String sqlLog   = "INSERT INTO log_celulares (celular_fk, empleado_fk, accion, detalle_cambio) VALUES (?, ?, ?, ?)";

        Connection con = ConexionDB.getInstancia().getConexion();

        try {
            con.setAutoCommit(false); 

            int idVenta;
            try (PreparedStatement ps = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, v.getCliente().getId());
                ps.setInt(2, v.getEmpleado().getId());
                ps.setDate(3, new java.sql.Date(v.getFecha().getTime()));
                ps.setDouble(4, v.getSubtotal());
                ps.setDouble(5, v.getIva());
                ps.setDouble(6, v.getTotal());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    idVenta = rs.getInt(1);
                }
            }

            try (PreparedStatement psItem = con.prepareStatement(sqlItem);
                 PreparedStatement psStock = con.prepareStatement(sqlStock);
                 PreparedStatement psLog = con.prepareStatement(sqlLog)) {

                for (ItemVenta item : v.getItems()) {
                    psItem.setInt(1, idVenta);
                    psItem.setInt(2, item.getCelular().getId());
                    psItem.setInt(3, item.getCantidad());
                    psItem.setDouble(4, item.getPrecioUnitario());
                    psItem.setDouble(5, item.getSubtotal());
                    psItem.executeUpdate();

                    psStock.setInt(1, item.getCantidad());
                    psStock.setInt(2, item.getCelular().getId());
                    psStock.executeUpdate();

                    psLog.setInt(1, item.getCelular().getId());
                    psLog.setInt(2, v.getEmpleado().getId());
                    psLog.setString(3, "VENTA");
                    psLog.setString(4, "Cantidad: " + item.getCantidad()
                                        + " - Venta ID: " + idVenta);
                    psLog.executeUpdate();
                }
            }

            con.commit(); 
            System.out.println("Venta registrada con id " + idVenta);

        } catch (SQLException e) {
            try {
                con.rollback(); 
                System.out.println("Transaccion revertida");
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            System.err.println("Error al insertar venta: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
            }
        }
    }

    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.fecha, v.subtotal, v.iva, v.total, " +
                     "c.id AS cliente_id, c.nombre AS cliente_nombre, " +
                     "e.id AS empleado_id, e.nombre AS empleado_nombre " +
                     "FROM ventas v " +
                     "JOIN cliente cl ON v.cliente_fk = cl.id " +
                     "JOIN persona c ON cl.id = c.id " +
                     "JOIN empleado em ON v.empleado_fk = em.id " +
                     "JOIN persona e ON em.id = e.id " +
                     "ORDER BY v.id";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                v.setFecha(rs.getDate("fecha"));
                v.setSubtotal(rs.getDouble("subtotal"));
                v.setIva(rs.getDouble("iva"));
                v.setTotal(rs.getDouble("total"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_id"));
                c.setNombre(rs.getString("cliente_nombre"));
                v.setCliente(c);

                Empleado e = new Empleado();
                e.setId(rs.getInt("empleado_id"));
                e.setNombre(rs.getString("empleado_nombre"));
                v.setEmpleado(e);

                v.setItems(listarItemsDeVenta(v.getId(), con));

                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    public Venta buscarPorId(int id) {
        Venta v = null;
        String sql = "SELECT v.id, v.fecha, v.subtotal, v.iva, v.total, " +
                     "c.id AS cliente_id, c.nombre AS cliente_nombre, " +
                     "e.id AS empleado_id, e.nombre AS empleado_nombre " +
                     "FROM ventas v " +
                     "JOIN cliente cl ON v.cliente_fk = cl.id " +
                     "JOIN persona c ON cl.id = c.id " +
                     "JOIN empleado em ON v.empleado_fk = em.id " +
                     "JOIN persona e ON em.id = e.id " +
                     "WHERE v.id = ?";
        Connection con = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new Venta();
                    v.setId(rs.getInt("id"));
                    v.setFecha(rs.getDate("fecha"));
                    v.setSubtotal(rs.getDouble("subtotal"));
                    v.setIva(rs.getDouble("iva"));
                    v.setTotal(rs.getDouble("total"));

                    Cliente c = new Cliente();
                    c.setId(rs.getInt("cliente_id"));
                    c.setNombre(rs.getString("cliente_nombre"));
                    v.setCliente(c);

                    Empleado e = new Empleado();
                    e.setId(rs.getInt("empleado_id"));
                    e.setNombre(rs.getString("empleado_nombre"));
                    v.setEmpleado(e);

                    v.setItems(listarItemsDeVenta(v.getId(), con));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        }
        return v;
    }

    private List<ItemVenta> listarItemsDeVenta(int idVenta, Connection con) throws SQLException {
        List<ItemVenta> items = new ArrayList<>();
        String sql = "SELECT d.id, d.cantidad, d.precio_unitario, d.subtotal, " +
                     "c.id AS celular_id, c.modelo AS celular_modelo " +
                     "FROM detalle_venta d " +
                     "JOIN celulares c ON d.celular_fk = c.id " +
                     "WHERE d.venta_fk = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemVenta item = new ItemVenta();
                    item.setId(rs.getInt("id"));
                    item.setCantidad(rs.getInt("cantidad"));
                    item.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    item.setSubtotal(rs.getDouble("subtotal"));

                    Celular c = new Celular();
                    c.setId(rs.getInt("celular_id"));
                    c.setModelo(rs.getString("celular_modelo"));
                    item.setCelular(c);

                    items.add(item);
                }
            }
        }
        return items;
    }
}
