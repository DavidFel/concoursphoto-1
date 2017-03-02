(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('site-user', {
            parent: 'entity',
            url: '/site-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.siteUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/site-user/site-users.html',
                    controller: 'SiteUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('siteUser');
                    $translatePartialLoader.addPart('userType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('site-user-detail', {
            parent: 'site-user',
            url: '/site-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.siteUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/site-user/site-user-detail.html',
                    controller: 'SiteUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('siteUser');
                    $translatePartialLoader.addPart('userType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SiteUser', function($stateParams, SiteUser) {
                    return SiteUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'site-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('site-user-detail.edit', {
            parent: 'site-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-user/site-user-dialog.html',
                    controller: 'SiteUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SiteUser', function(SiteUser) {
                            return SiteUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('site-user.new', {
            parent: 'site-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-user/site-user-dialog.html',
                    controller: 'SiteUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                birthDate: null,
                                email: null,
                                address: null,
                                jobTitle: null,
                                charte: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('site-user', null, { reload: 'site-user' });
                }, function() {
                    $state.go('site-user');
                });
            }]
        })
        .state('site-user.edit', {
            parent: 'site-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-user/site-user-dialog.html',
                    controller: 'SiteUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SiteUser', function(SiteUser) {
                            return SiteUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('site-user', null, { reload: 'site-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('site-user.delete', {
            parent: 'site-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-user/site-user-delete-dialog.html',
                    controller: 'SiteUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SiteUser', function(SiteUser) {
                            return SiteUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('site-user', null, { reload: 'site-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
