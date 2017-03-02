(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoCommentDetailController', UserPhotoCommentDetailController);

    UserPhotoCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserPhotoComment', 'SiteUser', 'Photo'];

    function UserPhotoCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, UserPhotoComment, SiteUser, Photo) {
        var vm = this;

        vm.userPhotoComment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('concoursphotoApp:userPhotoCommentUpdate', function(event, result) {
            vm.userPhotoComment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
