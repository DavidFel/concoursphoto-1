(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('theme', {
            parent: 'entity',
            url: '/theme',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.theme.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/theme/themes.html',
                    controller: 'ThemeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('theme');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('theme-detail', {
            parent: 'theme',
            url: '/theme/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.theme.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/theme/theme-detail.html',
                    controller: 'ThemeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('theme');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Theme', function($stateParams, Theme) {
                    return Theme.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'theme',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('theme-detail.edit', {
            parent: 'theme-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/theme/theme-dialog.html',
                    controller: 'ThemeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Theme', function(Theme) {
                            return Theme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('theme.new', {
            parent: 'theme',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/theme/theme-dialog.html',
                    controller: 'ThemeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('theme', null, { reload: 'theme' });
                }, function() {
                    $state.go('theme');
                });
            }]
        })
        .state('theme.edit', {
            parent: 'theme',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/theme/theme-dialog.html',
                    controller: 'ThemeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Theme', function(Theme) {
                            return Theme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('theme', null, { reload: 'theme' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('theme.delete', {
            parent: 'theme',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/theme/theme-delete-dialog.html',
                    controller: 'ThemeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Theme', function(Theme) {
                            return Theme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('theme', null, { reload: 'theme' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
