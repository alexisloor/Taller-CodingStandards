package fixed;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

class Menu {
    Map<String, Double> items;
    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());

    Menu() {
        items = new HashMap<>();
        items.put("Burger", 10.0);
        items.put("Pizza", 15.0);
        items.put("Salad", 8.0);
        items.put("Pasta", 12.0);
    }

    void show() {
        LOGGER.info("Menú:");
        for (Map.Entry<String, Double> item : items.entrySet()) {
            LOGGER.info(item.getKey() + ": $" + item.getValue());
        }
    }

    boolean isAvailable(String nombrePlato) {
        LOGGER.info("Aquí estoy en el método isAvailable");
        return items.containsKey(nombrePlato);
    }

    double getPrecio(String nombrePlato) {
        return items.get(nombrePlato);
    }
}

class Pedido {
    Map<String, Integer> items;

    Pedido() {
        items = new HashMap<>();
    }

    void agregar(String nombrePlato, int cantidad) {
        items.put(nombrePlato, cantidad);
    }

    Map<String, Integer> getItems() {
        return items;
    }

    int getCantidadTotal() {
        int total = 0;
        for (int cantidad : items.values()) {
            total += cantidad;
        }
        return total;
    }
}

class CalculadoraTotal {
    private static final double COSTO_BASE = 5;

    double calcularTotal(Pedido pedido, Menu menu) {
        double costoTotal = COSTO_BASE;
        int cantidadTotal = 0;

        for (Map.Entry<String, Integer> item : pedido.getItems().entrySet()) {
            costoTotal += menu.getPrecio(item.getKey()) * item.getValue();
            cantidadTotal += item.getValue();
        }

        double descuento = 0;
        if (cantidadTotal > 5) {
            descuento = 0.1;
        } else if (cantidadTotal > 10) {
            descuento = 0.2;
        }

        costoTotal -= costoTotal * descuento;

        return costoTotal;
    }
}

public class MiPrograma {
    private static final Logger LOGGER = Logger.getLogger(MiPrograma.class.getName());

    public static void main(String[] args) {
        Menu menu = new Menu();
        Pedido pedido = new Pedido();
        CalculadoraTotal calculadora = new CalculadoraTotal();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu.show();

            LOGGER.info("Ingrese el nombre del plato para ordenar o 'listo' para terminar: ");
            String nombrePlato = scanner.nextLine();
            if (nombrePlato.equals("listo")) break;

            if (!menu.isAvailable(nombrePlato)) {
                LOGGER.info("Plato no disponible. Por favor, seleccione otro.");
                continue;
            }

            LOGGER.info(String.format("Ingrese la cantidad para %s: ",nombrePlato));
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            if (cantidad <= 0) {
                LOGGER.info("Cantidad inválida. Por favor, vuelva a ingresar.");
                continue;
            }

            pedido.agregar(nombrePlato, cantidad);
        }

        double costoTotal = calculadora.calcularTotal(pedido, menu);
        int cantidadTotal = pedido.getCantidadTotal();

        if (cantidadTotal > 100) {
            LOGGER.info("La cantidad del pedido excede el límite máximo. Por favor, vuelva a ingresar.");
            scanner.close();
            return;
        }

        LOGGER.info("Tu Pedido:");
        for (Map.Entry<String, Integer> item : pedido.getItems().entrySet()) {
            LOGGER.info(item.getKey() + ": " + item.getValue());
        }

        LOGGER.info(String.format("Costo Total: $%f",costoTotal));
        LOGGER.info("¿Confirmar pedido (sí/no)?: ");
        String confirmar = scanner.nextLine();
        
        if (!confirmar.equalsIgnoreCase("sí")) {
            LOGGER.info("Pedido cancelado.");
            LOGGER.info("-1");
            scanner.close();
            return;
        }
        scanner.close();
        LOGGER.info(String.format("Pedido confirmado. El costo total es: $%f",costoTotal));
        
    }
}
