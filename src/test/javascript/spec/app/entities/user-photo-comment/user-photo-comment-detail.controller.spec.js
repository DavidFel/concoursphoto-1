'use strict';

describe('Controller Tests', function() {

    describe('UserPhotoComment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserPhotoComment, MockSiteUser, MockPhoto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserPhotoComment = jasmine.createSpy('MockUserPhotoComment');
            MockSiteUser = jasmine.createSpy('MockSiteUser');
            MockPhoto = jasmine.createSpy('MockPhoto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserPhotoComment': MockUserPhotoComment,
                'SiteUser': MockSiteUser,
                'Photo': MockPhoto
            };
            createController = function() {
                $injector.get('$controller')("UserPhotoCommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'concoursphotoApp:userPhotoCommentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
