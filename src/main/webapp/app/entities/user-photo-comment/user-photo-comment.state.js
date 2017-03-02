(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-photo-comment', {
            parent: 'entity',
            url: '/user-photo-comment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.userPhotoComment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comments.html',
                    controller: 'UserPhotoCommentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPhotoComment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-photo-comment-detail', {
            parent: 'user-photo-comment',
            url: '/user-photo-comment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.userPhotoComment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comment-detail.html',
                    controller: 'UserPhotoCommentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPhotoComment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserPhotoComment', function($stateParams, UserPhotoComment) {
                    return UserPhotoComment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-photo-comment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-photo-comment-detail.edit', {
            parent: 'user-photo-comment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comment-dialog.html',
                    controller: 'UserPhotoCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPhotoComment', function(UserPhotoComment) {
                            return UserPhotoComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-photo-comment.new', {
            parent: 'user-photo-comment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comment-dialog.html',
                    controller: 'UserPhotoCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comment: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-photo-comment', null, { reload: 'user-photo-comment' });
                }, function() {
                    $state.go('user-photo-comment');
                });
            }]
        })
        .state('user-photo-comment.edit', {
            parent: 'user-photo-comment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comment-dialog.html',
                    controller: 'UserPhotoCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPhotoComment', function(UserPhotoComment) {
                            return UserPhotoComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-photo-comment', null, { reload: 'user-photo-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-photo-comment.delete', {
            parent: 'user-photo-comment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-photo-comment/user-photo-comment-delete-dialog.html',
                    controller: 'UserPhotoCommentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserPhotoComment', function(UserPhotoComment) {
                            return UserPhotoComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-photo-comment', null, { reload: 'user-photo-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
