(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoVoteDetailController', UserPhotoVoteDetailController);

    UserPhotoVoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserPhotoVote', 'SiteUser', 'Photo'];

    function UserPhotoVoteDetailController($scope, $rootScope, $stateParams, previousState, entity, UserPhotoVote, SiteUser, Photo) {
        var vm = this;

        vm.userPhotoVote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('concoursphotoApp:userPhotoVoteUpdate', function(event, result) {
            vm.userPhotoVote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
