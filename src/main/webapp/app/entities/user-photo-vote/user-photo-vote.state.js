(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-photo-vote', {
            parent: 'entity',
            url: '/user-photo-vote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.userPhotoVote.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-photo-vote/user-photo-votes.html',
                    controller: 'UserPhotoVoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPhotoVote');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-photo-vote-detail', {
            parent: 'user-photo-vote',
            url: '/user-photo-vote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.userPhotoVote.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-photo-vote/user-photo-vote-detail.html',
                    controller: 'UserPhotoVoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPhotoVote');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserPhotoVote', function($stateParams, UserPhotoVote) {
                    return UserPhotoVote.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-photo-vote',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-photo-vote-detail.edit', {
            parent: 'user-photo-vote-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-vote/user-photo-vote-dialog.html',
                    controller: 'UserPhotoVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPhotoVote', function(UserPhotoVote) {
                            return UserPhotoVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-photo-vote.new', {
            parent: 'user-photo-vote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-vote/user-photo-vote-dialog.html',
                    controller: 'UserPhotoVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                stars: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-photo-vote', null, { reload: 'user-photo-vote' });
                }, function() {
                    $state.go('user-photo-vote');
                });
            }]
        })
        .state('user-photo-vote.edit', {
            parent: 'user-photo-vote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-vote/user-photo-vote-dialog.html',
                    controller: 'UserPhotoVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPhotoVote', function(UserPhotoVote) {
                            return UserPhotoVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-photo-vote', null, { reload: 'user-photo-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-photo-vote.delete', {
            parent: 'user-photo-vote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-vote/user-photo-vote-delete-dialog.html',
                    controller: 'UserPhotoVoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserPhotoVote', function(UserPhotoVote) {
                            return UserPhotoVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-photo-vote', null, { reload: 'user-photo-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
