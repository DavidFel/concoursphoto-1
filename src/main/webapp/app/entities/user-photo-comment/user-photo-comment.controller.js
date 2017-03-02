(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoCommentController', UserPhotoCommentController);

    UserPhotoCommentController.$inject = ['$scope', '$state', 'UserPhotoComment'];

    function UserPhotoCommentController ($scope, $state, UserPhotoComment) {
        var vm = this;

        vm.userPhotoComments = [];

        loadAll();

        function loadAll() {
            UserPhotoComment.query(function(result) {
                vm.userPhotoComments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
