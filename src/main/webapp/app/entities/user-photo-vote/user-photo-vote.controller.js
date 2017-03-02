(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .controller('UserPhotoVoteController', UserPhotoVoteController);

    UserPhotoVoteController.$inject = ['$scope', '$state', 'UserPhotoVote'];

    function UserPhotoVoteController ($scope, $state, UserPhotoVote) {
        var vm = this;

        vm.userPhotoVotes = [];

        loadAll();

        function loadAll() {
            UserPhotoVote.query(function(result) {
                vm.userPhotoVotes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
