(function() {
    'use strict';

    angular
        .module('concoursphotoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('concours', {
            parent: 'entity',
            url: '/concours',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.concours.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/concours/concours.html',
                    controller: 'ConcoursController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('concours');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('concours-detail', {
            parent: 'concours',
            url: '/concours/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'concoursphotoApp.concours.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/concours/concours-detail.html',
                    controller: 'ConcoursDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('concours');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Concours', function($stateParams, Concours) {
                    return Concours.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'concours',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('concours-detail.edit', {
            parent: 'concours-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concours/concours-dialog.html',
                    controller: 'ConcoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Concours', function(Concours) {
                            return Concours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('concours.new', {
            parent: 'concours',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concours/concours-dialog.html',
                    controller: 'ConcoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                description: null,
                                location: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('concours', null, { reload: 'concours' });
                }, function() {
                    $state.go('concours');
                });
            }]
        })
        .state('concours.edit', {
            parent: 'concours',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concours/concours-dialog.html',
                    controller: 'ConcoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Concours', function(Concours) {
                            return Concours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('concours', null, { reload: 'concours' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('concours.delete', {
            parent: 'concours',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concours/concours-delete-dialog.html',
                    controller: 'ConcoursDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Concours', function(Concours) {
                            return Concours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('concours', null, { reload: 'concours' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
