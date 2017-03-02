'use strict';

describe('Controller Tests', function() {

    describe('Photo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPhoto, MockUserPhotoVote, MockUserPhotoComment, MockSiteUser, MockConcours;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPhoto = jasmine.createSpy('MockPhoto');
            MockUserPhotoVote = jasmine.createSpy('MockUserPhotoVote');
            MockUserPhotoComment = jasmine.createSpy('MockUserPhotoComment');
            MockSiteUser = jasmine.createSpy('MockSiteUser');
            MockConcours = jasmine.createSpy('MockConcours');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Photo': MockPhoto,
                'UserPhotoVote': MockUserPhotoVote,
                'UserPhotoComment': MockUserPhotoComment,
                'SiteUser': MockSiteUser,
                'Concours': MockConcours
            };
            createController = function() {
                $injector.get('$controller')("PhotoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'concoursphotoApp:photoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
