import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;

import static java.awt.SystemColor.menu;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spyRestaurant = Mockito.spy(restaurant);
        //Mockito.when(spyRes.getCurrentTime()).thenReturn(LocalTime.parse("10:30:00"));
        Mockito.doReturn(LocalTime.parse("20:00:00")).when(spyRestaurant).getCurrentTime();
        assertTrue(spyRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spyRestaurant = Mockito.spy(restaurant);
        //Mockito.when(spyRest.getCurrentTime()).thenReturn(LocalTime.parse("00:00:00"));
        Mockito.doReturn(LocalTime.parse("00:00:00")).when(spyRestaurant).getCurrentTime();
        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void total_order_value_is_displayed_on_selecting_items_from_the_menu() throws itemNotFoundException {

        restaurant.addToMenu("Sweet corn soup",119);
        int initialMenuSize = restaurant.getMenu().size();
        //assertEquals(initialMenuSize,restaurant.getMenu().size());
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
        assertEquals(388,restaurant.calculateTotalOrderValue(restaurant.getMenu()));
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+2,restaurant.getMenu().size());
        assertEquals(707,restaurant.calculateTotalOrderValue(restaurant.getMenu()));
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
        assertEquals(438,restaurant.calculateTotalOrderValue(restaurant.getMenu()));
    }


}