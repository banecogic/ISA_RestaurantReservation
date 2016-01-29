'use strict';

describe('Meal Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMeal, MockRestaurant;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMeal = jasmine.createSpy('MockMeal');
        MockRestaurant = jasmine.createSpy('MockRestaurant');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Meal': MockMeal,
            'Restaurant': MockRestaurant
        };
        createController = function() {
            $injector.get('$controller')("MealDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'rrappApp:mealUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
