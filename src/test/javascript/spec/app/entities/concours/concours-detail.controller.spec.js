'use strict';

describe('Controller Tests', function() {

    describe('Concours Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConcours, MockTheme;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConcours = jasmine.createSpy('MockConcours');
            MockTheme = jasmine.createSpy('MockTheme');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Concours': MockConcours,
                'Theme': MockTheme
            };
            createController = function() {
                $injector.get('$controller')("ConcoursDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'concoursphotoApp:concoursUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
