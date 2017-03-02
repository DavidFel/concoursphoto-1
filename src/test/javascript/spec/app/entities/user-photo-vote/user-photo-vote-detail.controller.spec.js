'use strict';

describe('Controller Tests', function() {

    describe('UserPhotoVote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserPhotoVote, MockSiteUser, MockPhoto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserPhotoVote = jasmine.createSpy('MockUserPhotoVote');
            MockSiteUser = jasmine.createSpy('MockSiteUser');
            MockPhoto = jasmine.createSpy('MockPhoto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserPhotoVote': MockUserPhotoVote,
                'SiteUser': MockSiteUser,
                'Photo': MockPhoto
            };
            createController = function() {
                $injector.get('$controller')("UserPhotoVoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'concoursphotoApp:userPhotoVoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
