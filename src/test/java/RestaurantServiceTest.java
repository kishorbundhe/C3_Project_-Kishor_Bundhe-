import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    @Order(1)
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {

        assertThat(service.findRestaurantByName("Amelie's cafe").getName(), equalTo("Amelie's cafe"));
    }


    @Test
    @Order(2)
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {

        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Pantry d'or"));
    }

    @BeforeAll
    private void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    @Order(3)
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    @Order(4)
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    @Order(5)
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
}